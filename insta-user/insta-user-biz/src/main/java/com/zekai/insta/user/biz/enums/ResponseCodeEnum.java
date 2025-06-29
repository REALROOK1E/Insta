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
    SEX_VALID_FAIL("User_03", "武装直升机"),
    INTRODUCTION_VALID_FAIL("User_04", "你没有那么丰富，也没有那么匮乏"),
    INSTA_ID_VALID_FAIL("User_05","id错了" ), NICK_NAME_VALID_FAIL("0","000" );

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
