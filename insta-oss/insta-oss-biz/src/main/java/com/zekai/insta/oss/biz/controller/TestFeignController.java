package com.zekai.insta.oss.biz.controller;

import com.zekai.framework.common.response.Response;
import com.zekai.framework.oplog.aspect.ApiOperationLog;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;
/**
 * @author: ZeKai
 * @date: 2025/7/2
 * @description:
 **/
@RestController
@RequestMapping("/file")
@Slf4j
public class TestFeignController {

    @PostMapping(value = "/test")
    @ApiOperationLog(description = "Feign 测试接口")
    public Response<?> test() {
        return Response.success();
    }

}