package com.zekai.insta.auth.Controller;

import com.zekai.framework.common.response.Response;
import com.zekai.framework.oplog.aspect.ApiOperationLog;
import com.zekai.insta.auth.model.vo.user.UserLoginReqVO;
import com.zekai.insta.auth.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: ZeKai
 * @date: 2025/6/21
 * @description:
 **/
@RestController
@RequestMapping("auth/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    @ApiOperationLog(description = "用户登录/注册")
    public Response<String> loginAndRegister(@Validated @RequestBody UserLoginReqVO userLoginReqVO) {
        return userService.loginAndRegister(userLoginReqVO);
    }

    @PostMapping("/logout")
    @ApiOperationLog(description = "账号登出")
    public Response<?> logout(@RequestHeader("userId") String userId) {
        return userService.logout(Long.valueOf(userId));
    }

}
