package com.zekai.insta.auth.Controller;

import com.zekai.framework.common.response.Response;

import com.zekai.insta.auth.model.vo.verifycode.SendCodeReqVO;
import com.zekai.insta.auth.service.impl.VerifyCodeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ZeKai
 * @date: 2025/6/20
 * @description:
 **/
@RestController
@Slf4j
public class VerifyMsgController {
    @Resource
    private VerifyCodeService verificationCodeService;

    @PostMapping("/verification/code/send")
    //@ApiOperationLog(description = "发送短信验证码")
    public Response<?> send(@Validated @RequestBody SendCodeReqVO sendVerificationCodeReqVO) {
        return verificationCodeService.send(sendVerificationCodeReqVO);
    }
}
