package com.zekai.framework.common.Exception;

import lombok.Getter;

/**
 * @author: ZeKai
 * @date: 2025/5/30
 * @description:
 **/
@Getter
public class BizException extends RuntimeException {

    private String errorCode;
    // 错误信息
    private String errorMessage;

    public BizException(ExceptionInterface baseExceptionInterface) {
        this.errorCode = baseExceptionInterface.getCode();
        this.errorMessage = baseExceptionInterface.getMsg();
    }

}