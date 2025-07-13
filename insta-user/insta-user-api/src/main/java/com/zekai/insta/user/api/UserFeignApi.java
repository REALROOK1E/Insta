package com.zekai.insta.user.api;

import com.zekai.framework.common.response.Response;
import com.zekai.insta.user.constant.ApiConstants;
import com.zekai.insta.user.dto.req.FindUserByIdReqDTO;
import com.zekai.insta.user.dto.req.FindUserByPhoneReqDTO;
import com.zekai.insta.user.dto.req.RegisterUserReqDTO;
import com.zekai.insta.user.dto.req.UpdateUserPasswordReqDTO;
import com.zekai.insta.user.dto.resp.FindUserByIdRspDTO;
import com.zekai.insta.user.dto.resp.FindUserByPhoneRspDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: ZeKai
 * @date: 2025/7/7
 * @description:
 **/
@FeignClient(name = ApiConstants.SERVICE_NAME)
public interface UserFeignApi {

    String PREFIX = "/user";

    /**
     * 用户注册
     *
     * @param registerUserReqDTO
     * @return
     */
    @PostMapping(value = PREFIX + "/register")
    Response<Long> registerUser(@RequestBody RegisterUserReqDTO registerUserReqDTO);
    /*
    这个叫做封装客户端接口
     */
    @PostMapping(value = PREFIX + "/findByPhone")
    Response<FindUserByPhoneRspDTO> findByPhone(@RequestBody FindUserByPhoneReqDTO findUserByPhoneReqDTO);

    @PostMapping(value = PREFIX + "/password/update")
    Response<?> updatePassword(@RequestBody UpdateUserPasswordReqDTO updateUserPasswordReqDTO);

    /**
     * 根据用户 ID 查询用户信息
     *
     * @param findUserByIdReqDTO
     * @return
     */
    @PostMapping(value = PREFIX + "/findById")
    Response<FindUserByIdRspDTO> findById(@RequestBody FindUserByIdReqDTO findUserByIdReqDTO);
}
