package com.zekai.insta.user.api;

import com.zekai.framework.common.response.Response;
import com.zekai.insta.user.constant.ApiConstants;
import com.zekai.insta.user.dto.req.RegisterUserReqDTO;
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

}