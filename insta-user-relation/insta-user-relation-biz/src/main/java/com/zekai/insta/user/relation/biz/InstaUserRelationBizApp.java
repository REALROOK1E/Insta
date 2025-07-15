package com.zekai.insta.user.relation.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: ZeKai
 * @date: 2025/7/15
 * @description:
 **/
@SpringBootApplication
@EnableFeignClients(basePackages = "com.zekai.insta")
public class InstaUserRelationBizApp {
    public static void main(String[] args) {
        SpringApplication.run(InstaUserRelationBizApp.class, args);
    }
}
