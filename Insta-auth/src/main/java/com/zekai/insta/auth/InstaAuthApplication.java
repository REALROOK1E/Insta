package com.zekai.insta.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.zekai.insta.auth.domain.mapper")
@EnableFeignClients(basePackages = "com.zekai.insta")
public class InstaAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstaAuthApplication.class, args);
    }

}
