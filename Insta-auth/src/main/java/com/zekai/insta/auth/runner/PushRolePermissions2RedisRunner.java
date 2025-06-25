package com.zekai.insta.auth.runner;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zekai.framework.common.util.JsonUtils;
import com.zekai.insta.auth.constant.RedisKeyConst;
import com.zekai.insta.auth.domain.dataobject.PermissionDO;
import com.zekai.insta.auth.domain.dataobject.RoleDO;
import com.zekai.insta.auth.domain.dataobject.RolePermissionDO;
import com.zekai.insta.auth.domain.mapper.PermissionDOMapper;
import com.zekai.insta.auth.domain.mapper.RoleDOMapper;
import com.zekai.insta.auth.domain.mapper.RolePermissionDOMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author: ZeKai
 * @date: 2025/6/21
 * @description: 在 Spring Boot 启动时，把所有角色的权限数据查出来，按角色分组，序列化后存到 Redis，方便后续网关鉴权用。
 **/
@Component
@Slf4j
public class PushRolePermissions2RedisRunner implements ApplicationRunner {

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private RoleDOMapper roleDOMapper;
    @Resource
    private PermissionDOMapper permissionDOMapper;
    @Resource
    private RolePermissionDOMapper rolePermissionDOMapper;

    // 权限同步标记 Key，这里哪怕是我随机命名，下面的haskey也会是true，说明setnx那个语句被提前执行了？为什么
    private static final String PUSH_PERMISSION_FLAG = "这是7个锁22";

    @Override
    public void run(ApplicationArguments args) {
        log.info("==> 服务启动，开始同步角色权限数据到 Redis 中...");
        //删了flag之后下面应该是false，不存在了，但是每次删除完下面这个bool都是ture
        Boolean flagExists = redisTemplate.hasKey(PUSH_PERMISSION_FLAG);
        log.info("[DEBUG] Redis中push.permission.flag是否存在: {}", flagExists);
        //手动加一个key，成功了，说明我连的应该就是这个redis
        redisTemplate.opsForValue().set("如何呢", "又能怎");
        try {
            //用下面代码删了之后就正常了，但是我手动删就不行
          //  redisTemplate.delete(PUSH_PERMISSION_FLAG);
            // 是否能够同步数据: 原子操作，只有在键 PUSH_PERMISSION_FLAG 不存在时，才会设置该键的值为 "1"，并设置过期时间为 1 天
            log.info("==> 尝试获取锁，flag key: {}", PUSH_PERMISSION_FLAG);
            boolean canPushed = redisTemplate.opsForValue().setIfAbsent(PUSH_PERMISSION_FLAG, "1", 1, TimeUnit.DAYS);
            log.info("==> 获取锁结果: {}", canPushed);
            Boolean flagsts = redisTemplate.hasKey(PUSH_PERMISSION_FLAG);
            log.info("[DEBUG] 为啥为啥Redis中push.permission.flag是否存在: {}", flagsts);
            // 如果无法同步权限数据
            if (canPushed) {
                log.warn("==> 为啥呢？角色权限数据已经同步至 Redis 中，不再同步...");
                return;
            }

            // 查询出所有角色
            List<RoleDO> roleDOS = roleDOMapper.selectEnabledList();
            log.info("所有的角色列表：{}", roleDOS);
            if (CollUtil.isNotEmpty(roleDOS)) {
                // 拿到所有角色的 ID现在只有一个1
                List<Long> roleIds = roleDOS.stream().map(RoleDO::getId).toList();
                log.info("所有的角色id：{}", roleIds);
                // 根据角色 ID, 批量查询出所有角色对应的权限
                List<RolePermissionDO> rolePermissionDOS = rolePermissionDOMapper.selectByRoleIds(roleIds);
                // 按角色 ID 分组, 每个角色 ID 对应多个权限 ID
                log.info("分组后对应的权限id：{}", rolePermissionDOS);
                Map<Long, List<Long>> roleIdPermissionIdsMap = rolePermissionDOS.stream().collect(
                        Collectors.groupingBy(RolePermissionDO::getRoleId,
                                Collectors.mapping(RolePermissionDO::getPermissionId, Collectors.toList()))
                );
                //到目前一切正常
                // 查询 APP 端所有被启用的权限
                List<PermissionDO> permissionDOS = permissionDOMapper.selectAppEnabledList();
                log.info("所有的权限列表：{}", permissionDOS);
                // 权限 ID - 权限 DO
                Map<Long, PermissionDO> permissionIdDOMap = permissionDOS.stream().collect(
                        Collectors.toMap(PermissionDO::getId, permissionDO -> permissionDO)
                );

                // 组织 角色-权限 关系
                Map<String, List<String>> roleKeyPermissionsMap = Maps.newHashMap();

                // 循环所有角色
                roleDOS.forEach(roleDO -> {
                    // 当前角色 ID
                    Long roleId = roleDO.getId();
                    log.info("角色id是：{}", roleId);
                    // 当前角色 roleKey
                    String roleKey = roleDO.getRoleKey();
                    log.info("角色key是：{}", roleKey);
                    // 当前角色 ID 对应的权限 ID 集合
                    List<Long> permissionIds = roleIdPermissionIdsMap.get(roleId);
                    if (CollUtil.isNotEmpty(permissionIds)) {
                        List<String> permissionKeys = Lists.newArrayList();
                        permissionIds.forEach(permissionId -> {
                            // 根据权限 ID 获取具体的权限 DO 对象 好像就是这个没获取到
                            PermissionDO permissionDO = permissionIdDOMap.get(permissionId);
                            permissionKeys.add(permissionDO.getPermissionKey());
                            log.info("==> 权限key是：{} ", permissionDO.getPermissionKey());
                        });
                        roleKeyPermissionsMap.put(roleKey, permissionKeys);
                    }
                });

                // 同步至 Redis 中，方便后续网关查询 Redis, 用于鉴权
                roleKeyPermissionsMap.forEach((roleKey, permissions) -> {
                    String key = RedisKeyConst.buildRolePermissionsKey(roleKey);
                    log.info("==> 同步角色权限数据到 Redis 中, key: {}, 权限: {}", key, permissions);
                    redisTemplate.opsForValue().set(key, JsonUtils.toJsonString(permissions));
                });
            }

            log.info("==> 服务启动，成功同步角色权限数据到 Redis 中...");
        } catch (Exception e) {
            log.error("==> 同步角色权限数据到 Redis 中失败: ", e);
        }

    }
}