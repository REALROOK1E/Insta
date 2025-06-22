package com.zekai.insta.auth.Controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.common.JustForTest;
import com.zekai.framework.common.response.Response;
import com.zekai.framework.oplog.aspect.ApiOperationLog;
import com.zekai.insta.auth.alarm.AlarmInterface;
import com.zekai.insta.auth.model.vo.user.UserLoginReqVO;
import com.zekai.insta.auth.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.annotation.Validated;
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


