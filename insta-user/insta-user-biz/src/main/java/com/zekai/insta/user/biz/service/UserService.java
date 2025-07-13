package com.zekai.insta.user.biz.service;

import com.zekai.framework.common.response.Response;
import com.zekai.insta.user.biz.model.vo.UpdateUserInfoVO;
import com.zekai.insta.user.dto.req.FindUserByIdReqDTO;
import com.zekai.insta.user.dto.req.FindUserByPhoneReqDTO;
import com.zekai.insta.user.dto.req.RegisterUserReqDTO;
import com.zekai.insta.user.dto.req.UpdateUserPasswordReqDTO;
import com.zekai.insta.user.dto.resp.FindUserByIdRspDTO;
import com.zekai.insta.user.dto.resp.FindUserByPhoneRspDTO;

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
    /**
     * 根据手机号查询用户信息
     *
     * @param findUserByPhoneReqDTO
     * @return
     */
    Response<FindUserByPhoneRspDTO> findByPhone(FindUserByPhoneReqDTO findUserByPhoneReqDTO);
    // 省略...

    /**
     * 更新密码
     *
     * @param updateUserPasswordReqDTO
     * @return
     */
    Response<?> updatePassword(UpdateUserPasswordReqDTO updateUserPasswordReqDTO);

    Response<FindUserByIdRspDTO> findById(FindUserByIdReqDTO findUserByIdReqDTO);
}
