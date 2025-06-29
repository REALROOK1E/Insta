package com.zekai.insta.oss.biz.Exception;

import com.zekai.framework.common.Exception.BizException;
import com.zekai.framework.common.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: ZeKai
 * @date: 2025/6/28
 * @description:
 **/
@Slf4j
public class MinioException {

    //todo: exception

    @ExceptionHandler({ BizException.class })
    @ResponseBody
    public Response<Object> handleBizException(HttpServletRequest request, BizException e) {
        log.warn("{} request fail, errorCode: {}, errorMessage: {}", request.getRequestURI(), e.getErrorCode(), e.getErrorMessage());
        return Response.fail(e);
    }

}
