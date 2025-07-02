package com.zekai.insta.oss.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("com.zekai.insta.oss.biz.domain.mapper")
@EnableFeignClients(basePackages = "com.zekai.insta")
public class instaOssBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(instaOssBizApplication.class, args);
    }

}