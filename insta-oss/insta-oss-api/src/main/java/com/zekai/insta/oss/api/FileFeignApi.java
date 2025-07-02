package com.zekai.insta.oss.api;

import com.zekai.framework.common.response.Response;
import com.zekai.insta.oss.constant.ApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author: ZeKai
 * @date: 2025/7/2
 * @description: @FeignClient 是用来标记这个接口是一个 Feign 客户端的注解。
 * name = ApiConstants.SERVICE_NAME 指定了这个 Feign 客户端所调用的服务名称。这个名称通常是在注册中心（如 Eureka 或 Nacos）中注册的服务名称。
 * String PREFIX = "/file"; : 定义了一个前缀常量，用于接口中 URI 的路径前缀。
 * @PostMapping 注解标记这个方法将执行一个 HTTP POST 请求。
 * value = PREFIX + "/test" 指定了这个 POST 请求的路径，这里是 "/file/test"。
 **/
@FeignClient(name = ApiConstants.SERVICE_NAME)
public interface FileFeignApi {

    String PREFIX = "/file";

    @PostMapping(value = PREFIX + "/test")
    Response<?> test();

}