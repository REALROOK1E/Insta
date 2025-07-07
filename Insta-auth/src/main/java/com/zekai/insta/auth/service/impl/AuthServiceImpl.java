package com.zekai.insta.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateTime;
import com.google.common.base.Preconditions;
import com.zekai.framework.common.Exception.BizException;
import com.zekai.framework.common.enums.DeletedEnum;
import com.zekai.framework.common.enums.StatusEnum;
import com.zekai.framework.common.response.Response;
import com.zekai.framework.common.util.JsonUtils;
import com.zekai.insta.auth.constant.RedisKeyConst;
import com.zekai.insta.auth.constant.RoleConstants;
import com.zekai.insta.auth.enums.LoginType;
import com.zekai.insta.auth.enums.ResponseCodeEnum;
import com.zekai.insta.auth.filter.LoginUserContextHolder;
import com.zekai.insta.auth.model.vo.user.UpdatePasswordReqVO;
import com.zekai.insta.auth.model.vo.user.UserLoginReqVO;
import com.zekai.insta.auth.rpc.UserRpcService;
import com.zekai.insta.auth.service.AuthService;
import com.zekai.insta.user.dto.resp.FindUserByPhoneRspDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class AuthServiceImpl implements AuthService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserRpcService userRpcService;
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
                log.info("到这里应该没有什么意外。。。");
                // 通过手机号查询记录
                Long userIdTmp = userRpcService.registerUser(phone);

                // 若调用用户服务，返回的用户 ID 为空，则提示登录失败
                if (Objects.isNull(userIdTmp)) {
                    throw new BizException(ResponseCodeEnum.LOGIN_FAIL);
                }

                userId = userIdTmp;
                break;
            case PASSWORD: // 密码登录
             // 密码登录
                String password = userLoginReqVO.getPassword();
                // 根据手机号查询，原来是调数据库，现在是远程调用
                FindUserByPhoneRspDTO findUserByPhoneRspDTO = userRpcService.findUserByPhone(phone);

                // 判断该手机号是否注册
                if (Objects.isNull(findUserByPhoneRspDTO)) {
                    throw new BizException(ResponseCodeEnum.USER_NOT_FOUND);
                }

                // 拿到密文密码
                String encodePassword = findUserByPhoneRspDTO.getPassword();

                // 匹配密码是否一致
                boolean isPasswordCorrect = passwordEncoder.matches(password, encodePassword);

                // 如果不正确，则抛出业务异常，提示用户名或者密码不正确
                if (!isPasswordCorrect) {
                    throw new BizException(ResponseCodeEnum.PHONE_OR_PASSWORD_ERROR);
                }

                userId = findUserByPhoneRspDTO.getId();
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


    @Override
    public Response<?> updatePassword(UpdatePasswordReqVO updatePasswordReqVO) {
        // 新密码
        String newPassword = updatePasswordReqVO.getNewPassword();
        // 密码加密
        String encodePassword = passwordEncoder.encode(newPassword);

        // RPC: 调用用户服务：更新密码
        userRpcService.updatePassword(encodePassword);

        return Response.success();
    }

}
