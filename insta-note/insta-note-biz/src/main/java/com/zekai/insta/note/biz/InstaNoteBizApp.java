package com.zekai.insta.note.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: ZeKai
 * @date: 2025/7/7
 * @description:
 **/
@SpringBootApplication
@MapperScan("com.zekai.insta.note.biz.domain.mapper")
@EnableFeignClients("com.zekai.insta")
public class InstaNoteBizApp {
    public static void main(String[] args) {
        SpringApplication.run(InstaNoteBizApp.class, args);
    }
}
