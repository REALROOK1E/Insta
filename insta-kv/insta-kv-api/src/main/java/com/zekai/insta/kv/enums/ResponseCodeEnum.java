package com.zekai.insta.kv.enums;

import com.zekai.framework.common.Exception.ExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: ZeKai
 * @date: 2025/7/8
 * @description:
 **/
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements ExceptionInterface {

    // ----------- 通用异常状态码 -----------
    SYSTEM_ERROR("KV-10000", "出错啦，后台小哥正在努力修复中..."),
    PARAM_NOT_VALID("KV-10001", "参数错误"),

    // ----------- 业务异常状态码 -----------
    NOTE_CONTENT_NOT_FOUND("KV-20000", "该笔记内容不存在"),
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