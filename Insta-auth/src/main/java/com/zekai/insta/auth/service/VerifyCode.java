package com.zekai.insta.auth.service;

import com.zekai.framework.common.response.Response;
import com.zekai.insta.auth.model.vo.verifycode.SendCodeReqVO;

/**
 * @author: ZeKai
 * @date: 2025/6/20
 * @description: send a code
 **/

public interface VerifyCode {

    Response<?> send(SendCodeReqVO sendVerifyCodeVO);

}
