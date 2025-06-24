package com.zekai.insta.auth.Controller;


import com.zekai.framework.common.response.Response;
import com.zekai.insta.auth.alarm.AlarmInterface;

import jakarta.annotation.Resource;


import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;


import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ZeKai
 * @date: 2025/6/22
 * @description:
 **/

@RestController
@RequestMapping("/user")
@Slf4j
public class TestController {


    @Resource
    private AlarmInterface alarm;

    @GetMapping("/test")

    public Response<User> test(@RequestBody User user) {

        alarm.send("码头有大，码头来了");

        return Response.success(user);
    }


}


