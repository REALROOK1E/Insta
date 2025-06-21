package com.zekai.insta.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateTime;
import com.google.common.collect.Lists;
import com.zekai.framework.common.Exception.BizException;
import com.zekai.framework.common.enums.DeletedEnum;
import com.zekai.framework.common.enums.StatusEnum;
import com.zekai.framework.common.response.Response;
import com.zekai.framework.common.util.JsonUtils;
import com.zekai.insta.auth.constant.RedisKeyConst;
import com.zekai.insta.auth.constant.RoleConstants;
import com.zekai.insta.auth.domain.dataobject.UserDO;
import com.zekai.insta.auth.domain.dataobject.UserRoleDO;
import com.zekai.insta.auth.domain.mapper.UserDOMapper;
import com.zekai.insta.auth.domain.mapper.UserRoleDOMapper;
import com.zekai.insta.auth.enums.LoginType;
import com.zekai.insta.auth.enums.ResponseCodeEnum;
import com.zekai.insta.auth.model.vo.user.UserLoginReqVO;
import com.zekai.insta.auth.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author: ZeKai
 * @date: 2025/6/21
 * @description:
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserDOMapper userDOMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private UserRoleDOMapper userRoleDOMapper;
    /**
     * 登录与注册
     *
     * @param userLoginReqVO
     * @return
     */

    @Override
    public Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO) {
        String phone = userLoginReqVO.getPhone();
        Integer type = userLoginReqVO.getType();

        LoginType loginTypeEnum = LoginType.valueOf(type);

        Long userId = null;

        // 判断登录类型
        switch (loginTypeEnum) {
            case VERIFICATION_CODE: // 验证码登录
                String verificationCode = userLoginReqVO.getCode();

                // 校验入参验证码是否为空
                if (StringUtils.isBlank(verificationCode)) {
                    return Response.fail(ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode(), "验证码不能为空");
                }

                // 构建验证码 Redis Key
                String key = RedisKeyConst.buildVerificationCodeKey(phone);
                // 查询存储在 Redis 中该用户的登录验证码
                String sentCode = (String) redisTemplate.opsForValue().get(key);

                // 判断用户提交的验证码，与 Redis 中的验证码是否一致
                if (!StringUtils.equals(verificationCode, sentCode)) {
                    throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_ERROR);
                }

                // 通过手机号查询记录
                UserDO userDO = userDOMapper.selectByPhone(phone);

                log.info("==> 用户是否注册, phone: {}, userDO: {}", phone, JsonUtils.toJsonString(userDO));

                // 判断是否注册
                if (Objects.isNull(userDO)) {
                    // 若此用户还没有注册，系统自动注册该用户
                    userId = registerUser(phone);

                } else {
                    // 已注册，则获取其用户 ID
                    userId = userDO.getId();
                }
                break;
            case PASSWORD: // 密码登录
                // todo

                break;
            default:
                break;
        }

        // SaToken 登录用户，并返回 token 令牌
        StpUtil.login(userId);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return Response.success(tokenInfo.tokenValue);
    }
    @Transactional(rollbackFor = Exception.class)//原子性
    public Long registerUser(String phone) {
        Long instaId = redisTemplate.opsForValue().increment(RedisKeyConst.INSTA_ID_GENERATOR_KEY);
        UserDO userDO = UserDO.builder()
                .phone(phone)
                .instaId(String.valueOf(instaId)) // 自动生成小红书号 ID
                .username("小红薯" + instaId) // 自动生成昵称, 如：小红薯10000
                .status(StatusEnum.ENABLE.getValue()) // 状态为启用
                .createTime(DateTime.now())
                .updateTime(DateTime.now())
                .isDeleted(DeletedEnum.NO.getValue()) // 逻辑删除
                .build();
        // 添加入库
        userDOMapper.insert(userDO);

        // 获取刚刚添加入库的用户 ID
        Long userId = userDO.getId();

        // 给该用户分配一个默认角色
        UserRoleDO userRoleDO = UserRoleDO.builder()
                .userId(userId)
                .roleId(RoleConstants.COMMON_USER_ROLE_ID)
                .createTime(DateTime.now())
                .updateTime(DateTime.now())
                .isDeleted(DeletedEnum.NO.getValue())
                .build();
        userRoleDOMapper.insert(userRoleDO);

        // 将该用户的角色 ID 存入 Redis 中
        List<Long> roles = Lists.newArrayList();
        roles.add(RoleConstants.COMMON_USER_ROLE_ID);
        String userRolesKey = RedisKeyConst.buildUserRoleKey(phone);
        redisTemplate.opsForValue().set(userRolesKey, JsonUtils.toJsonString(roles));

        return userId;
    }

}
