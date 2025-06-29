package com.zekai.insta.user.biz.enums;

import com.zekai.framework.common.Exception.ExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: ZeKai
 * @date: 2025/6/29
 * @description:
 **/
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements ExceptionInterface {

    // ----------- 通用异常状态码 -----------
    SYSTEM_ERROR("User_00", "用户错误?"),
    PARAM_NOT_VALID("User_01", "错误参数"),

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
