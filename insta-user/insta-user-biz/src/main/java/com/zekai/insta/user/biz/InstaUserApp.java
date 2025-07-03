package com.zekai.insta.user.biz;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: ZeKai
 * @date: 2025/6/29
 * @description:
 **/
@SpringBootApplication
@MapperScan("com.zekai.insta.user.biz.domain.mapper")
@EnableFeignClients(basePackages = "com.zekai.insta")
public class InstaUserApp {
    public static void main(String[] args) {
        SpringApplication.run(InstaUserApp.class, args);
    }
}
