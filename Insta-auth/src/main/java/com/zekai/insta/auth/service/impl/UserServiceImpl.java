package com.zekai.insta.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateTime;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zekai.framework.common.Exception.BizException;
import com.zekai.framework.common.enums.DeletedEnum;
import com.zekai.framework.common.enums.StatusEnum;
import com.zekai.framework.common.response.Response;
import com.zekai.framework.common.util.JsonUtils;
import com.zekai.insta.auth.constant.RedisKeyConst;
import com.zekai.insta.auth.constant.RoleConstants;
import com.zekai.insta.auth.domain.dataobject.RoleDO;
import com.zekai.insta.auth.domain.dataobject.UserDO;
import com.zekai.insta.auth.domain.dataobject.UserRoleDO;
import com.zekai.insta.auth.domain.mapper.RoleDOMapper;
import com.zekai.insta.auth.domain.mapper.UserDOMapper;
import com.zekai.insta.auth.domain.mapper.UserRoleDOMapper;
import com.zekai.insta.auth.enums.LoginType;
import com.zekai.insta.auth.enums.ResponseCodeEnum;
import com.zekai.insta.auth.filter.LoginUserContextHolder;
import com.zekai.insta.auth.model.vo.user.UpdatePasswordReqVO;
import com.zekai.insta.auth.model.vo.user.UserLoginReqVO;
import com.zekai.insta.auth.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private RoleDOMapper roleDOMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
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
        if (Objects.isNull(loginTypeEnum)) {
            throw new BizException(ResponseCodeEnum.LOGIN_TYPE_ERROR);
        }
        Long userId = null;

        // 判断登录类型
        switch (loginTypeEnum) {
            case VERIFICATION_CODE: // 验证码登录
                String verificationCode = userLoginReqVO.getCode();

                // 校验入参验证码是否为空
                Preconditions.checkArgument(StringUtils.isNotBlank(verificationCode), "验证码不能为空");
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
             // 密码登录
                String password = userLoginReqVO.getPassword();
                // 根据手机号查询
                UserDO userDO1 = userDOMapper.selectByPhone(phone);

                // 判断该手机号是否注册
                if (Objects.isNull(userDO1)) {
                    throw new BizException(ResponseCodeEnum.USER_NOT_FOUND);
                }

                // 拿到密文密码
                String encodePassword = userDO1.getPassword();

                // 匹配密码是否一致
                boolean isPasswordCorrect = passwordEncoder.matches(password, encodePassword);

                // 如果不正确，则抛出业务异常，提示用户名或者密码不正确
                if (!isPasswordCorrect) {
                    throw new BizException(ResponseCodeEnum.PHONE_OR_PASSWORD_ERROR);
                }

                userId = userDO1.getId();
                break;
            default:
                break;
        }

        // SaToken 登录用户，并返回 token 令牌
        StpUtil.login(userId);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return Response.success(tokenInfo.tokenValue);
    }





    @Override
    public Response<?> logout() {
        // 退出登录 (指定用户 ID)
        Long userId = LoginUserContextHolder.getUserId();
        StpUtil.logout(userId);
        return Response.success();
    }



    /**
     * @param updatePasswordReqVO
     * @return
     */
    @Override
    public Response<?> updatePassword(UpdatePasswordReqVO updatePasswordReqVO) {
        // 新密码
        String newPassword = updatePasswordReqVO.getNewPassword();
        // 密码加密
        String encodePassword = passwordEncoder.encode(newPassword);

        // 获取当前请求对应的用户 ID
        Long userId = LoginUserContextHolder.getUserId();

        UserDO userDO = UserDO.builder()
                .id(userId)
                .password(encodePassword)
                .updateTime(DateTime.now())
                .build();
        // 更新密码
        userDOMapper.updateByPrimaryKeySelective(userDO);

        return Response.success();
    }

    @Transactional(rollbackFor = Exception.class)//原子性
    public Long registerUser(String phone) {
      return transactionTemplate.execute(status ->  {
        try {
            Long instaId = redisTemplate.opsForValue().increment(RedisKeyConst.INSTA_ID_GENERATOR_KEY);
            log.info("电话号码是：{}", phone);
            log.info("id是：{}", instaId);
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

            // 给该用户构造一个对应的实体类，然后插入
            UserRoleDO userRoleDO = UserRoleDO.builder()
                    .userId(userId)
                    .roleId(RoleConstants.COMMON_USER_ROLE_ID)
                    .createTime(DateTime.now())
                    .updateTime(DateTime.now())
                    .isDeleted(DeletedEnum.NO.getValue())
                    .build();
            userRoleDOMapper.insert(userRoleDO);

            RoleDO roleDO =roleDOMapper.selectByPrimaryKey(RoleConstants.COMMON_USER_ROLE_ID);
            // 将该用户的角色 ID 存入 Redis 中，指定初始容量为 1，这样可以减少在扩容时的性能开销
            List<String> roles = new ArrayList<>(1);
            roles.add(roleDO.getRoleKey());
            String userRolesKey = RedisKeyConst.buildUserRoleKey(userId);
            redisTemplate.opsForValue().set(userRolesKey, JsonUtils.toJsonString(roles));

            return userId;
        } catch (Exception e) {
            status.setRollbackOnly(); // 标记事务为回滚
            log.error("==> 系统注册用户异常: ", e);
            return null;

        }
    });
}}
