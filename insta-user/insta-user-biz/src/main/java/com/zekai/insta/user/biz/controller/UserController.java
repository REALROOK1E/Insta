package com.zekai.insta.user.biz.controller;
import com.zekai.framework.common.response.Response;
import com.zekai.framework.oplog.aspect.ApiOperationLog;
import com.zekai.insta.user.biz.model.vo.UpdateUserInfoVO;
import com.zekai.insta.user.biz.service.UserService;
import com.zekai.insta.user.dto.req.RegisterUserReqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author: ZeKai
 * @date: 2025/6/29
 * @description:
 **/
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户信息修改
     *

     */
    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<?> updateUserInfo(@Validated UpdateUserInfoVO updateUserInfoVO) {
        return userService.updateUserInfo(updateUserInfoVO);
    }
    @PostMapping("/register")
    @ApiOperationLog(description = "用户注册")
    public Response<Long> register(@Validated @RequestBody RegisterUserReqDTO registerUserReqDTO) {
        return userService.register(registerUserReqDTO);
    }
}
