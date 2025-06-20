package com.zekai.insta.auth.Controller;


import com.zekai.framework.common.response.Response;
import com.zekai.insta.auth.domain.dataobject.UserDO;
import com.zekai.framework.oplog.aspect.ApiOperationLog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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


}
