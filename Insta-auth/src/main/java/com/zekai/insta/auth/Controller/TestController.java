package com.zekai.insta.auth.Controller;


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

    public String test() {

        alarm.send("警告，警告，码头有大，码头来了");

        return "我不是僵尸";
    }


}


