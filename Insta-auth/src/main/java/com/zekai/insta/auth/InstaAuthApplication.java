package com.zekai.insta.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zekai.insta.auth.domain.mapper")
public class InstaAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstaAuthApplication.class, args);
    }

}
