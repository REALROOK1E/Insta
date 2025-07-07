package com.zekai.insta.auth.rpc;

import com.zekai.framework.common.response.Response;
import com.zekai.insta.user.api.UserFeignApi;
import com.zekai.insta.user.dto.req.RegisterUserReqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: ZeKai
 * @date: 2025/7/7
 * @description:
 **/
@Component
@Slf4j
public class UserRpcService {

    @Resource
    private UserFeignApi userFeignApi;

    /**
     * 用户注册
     *
     * @param phone
     * @return
     */
    public Long registerUser(String phone) {
        RegisterUserReqDTO registerUserReqDTO = new RegisterUserReqDTO();
        registerUserReqDTO.setPhone(phone);
        log.info("在rpc层也没有问题");
        Response<Long> response = userFeignApi.registerUser(registerUserReqDTO);
        if (!response.isSuccess()) {
            return null;
        }

        return response.getData();
    }

}
