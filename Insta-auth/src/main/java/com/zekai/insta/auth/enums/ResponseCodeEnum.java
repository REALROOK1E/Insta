package com.zekai.insta.auth.enums;

import com.zekai.framework.common.Exception.ExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: ZeKai
 * @date: 2025/6/20
 * @description:
 **/
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements ExceptionInterface {

    // ----------- 通用异常状态码 -----------
    SYSTEM_ERROR("Auth_00", "Whats going on?"),
    PARAM_NOT_VALID("Auth_01", "ParamErr"),
    VERIFICATION_CODE_SEND_FREQUENTLY("Auth-02", "msg sent Too frequent, try again later"),
    VERIFICATION_CODE_ERROR("Auth_03", "验证码错误"),
    LOGIN_TYPE_ERROR("Auth_04", "登录类型错误"),
    USER_NOT_FOUND("Auth_05", "该用户不存在"),
    PHONE_OR_PASSWORD_ERROR("Auth_06", "手机号或密码错误"),
    LOGIN_FAIL("Auth_07", "登录失败"),
    // ----------- 业务异常状态码 -----------
    ;

    // 异常码
    private final String errorCode;
    // 错误信息
    private final String errorMessage;

    /**
     * @return
     */
    @Override
    public String getCode() {
        return "";
    }

    /**
     * @return
     */
    @Override
    public String getMsg() {
        return "";
    }
}
