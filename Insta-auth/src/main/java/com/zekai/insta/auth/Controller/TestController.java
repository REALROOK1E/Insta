package com.zekai.insta.auth.Controller;


import cn.dev33.satoken.stp.StpUtil;
import com.zekai.framework.common.response.Response;
import com.zekai.insta.auth.domain.dataobject.UserDO;
import com.zekai.framework.oplog.aspect.ApiOperationLog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ZeKai
 * @date: 2025/5/30
 * @description:
 **/
@RestController
public class TestController {

    @PostMapping("/test2")
    public Response<User>test2(@RequestBody @Validated User user){
        int i=1/0;
        return Response.success(user);
    }

    @RequestMapping("/user/doLogin")
    public String doLogin(String username, String password) {
        //simulate login
        if("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            return "登录成功";
        }
        return "登录失败";
    }
    @RequestMapping("/user/isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }
}
