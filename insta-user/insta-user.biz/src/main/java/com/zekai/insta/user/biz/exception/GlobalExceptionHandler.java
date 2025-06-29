package com.zekai.insta.user.biz.exception;

import com.zekai.framework.common.Exception.BizException;
import com.zekai.framework.common.Exception.ExceptionInterface;
import com.zekai.framework.common.response.Response;
import com.zekai.insta.user.biz.enums.ResponseCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: ZeKai
 * @date: 2025/6/29
 * @description:
 **/
@Slf4j
public class GlobalExceptionHandler  {




    @ExceptionHandler({ Exception.class })
    @ResponseBody
    public Response<Object> handleOtherException(HttpServletRequest request, Exception e) {
        log.error("{} request error, ", request.getRequestURI(), e);
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler({IllegalArgumentException.class })
    @ResponseBody
    public Response<Object> handleillegalException(HttpServletRequest request, Exception e) {
        // 参数错误异常码
        String errorCode = ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode();
        // 错误信息
        String errorMessage = e.getMessage();

        log.warn("{} request error, errorCode: {}, errorMessage: {}", request.getRequestURI(), errorCode, errorMessage);

        return Response.fail(errorCode, errorMessage);
    }

}
