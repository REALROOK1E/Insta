package com.zekai.insta.auth.Controller;

import com.zekai.framework.common.response.Response;
import com.zekai.framework.oplog.aspect.ApiOperationLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ZeKai
 * @date: 2025/5/30
 * @description:
 **/
@RestController
public class TestController {

    @GetMapping("/test")
    @ApiOperationLog(description = "测试接口")
    public Response<String> test() {
        return Response.success("Hello, Zekai");
    }
}
