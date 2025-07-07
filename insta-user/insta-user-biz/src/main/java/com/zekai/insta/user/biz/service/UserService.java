package com.zekai.insta.user.biz.service;

import com.zekai.framework.common.response.Response;
import com.zekai.insta.user.biz.model.vo.UpdateUserInfoVO;
import com.zekai.insta.user.dto.req.RegisterUserReqDTO;

/**
 * @author: ZeKai
 * @date: 2025/6/29
 * @description:
 **/
public interface UserService {

    Response<?> updateUserInfo(UpdateUserInfoVO updateUserInfoReqVO);

    /**
     * 用户注册
     *
     * @param registerUserReqDTO
     * @return
     */
    Response<Long> register(RegisterUserReqDTO registerUserReqDTO);
}
