package com.zekai.insta.auth.Controller;

import com.zekai.framework.common.response.Response;
import com.zekai.framework.oplog.aspect.ApiOperationLog;
import com.zekai.insta.auth.filter.LoginUserContextHolder;
import com.zekai.insta.auth.model.vo.user.UpdatePasswordReqVO;
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


    @PostMapping("/password/update")
    @ApiOperationLog(description = "修改密码")
    public Response<?> updatePassword(@Validated @RequestBody UpdatePasswordReqVO updatePasswordReqVO) {
        return userService.updatePassword(updatePasswordReqVO);
    }

    @PostMapping("/logout")
    @ApiOperationLog(description = "账号登出")
    public Response<?> logout() {
        return userService.logout();
    }

}
