package com.zekai.insta.auth.service;

import com.zekai.framework.common.response.Response;
import com.zekai.insta.auth.model.vo.user.UserLoginReqVO;

/**
 * @author: ZeKai
 * @date: 2025/6/21
 * @description:
 **/

public interface UserService
{
    Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO);
}
