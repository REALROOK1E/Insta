package com.zekai.insta.gateway.enums;
import com.zekai.framework.common.Exception.ExceptionInterface;
import com.zekai.framework.common.Exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * @author: ZeKai
 * @date: 2025/6/26
 * @description:
 **/
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements ExceptionInterface {

    // ----------- 通用异常状态码 -----------
    SYSTEM_ERROR("500", "系统繁忙，请稍后再试"),
    UNAUTHORIZED("401", "权限不足"),


    // ----------- 业务异常状态码 -----------
    ;

    // 异常码
    private final String errorCode;
    // 错误信息
    private final String errorMessage;

    @Override
    public String getCode() {
        return "";
    }

    @Override
    public String getMsg() {
        return "";
    }
}