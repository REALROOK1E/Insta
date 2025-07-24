package com.zekai.comment.biz;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Zekai
 * @date 2025/7/24
 * @Descripson :
 */
@SpringBootApplication
@MapperScan("com.zekai.comment.biz.domain.mapper")
public class InstaCommentApp {
    public static void main(String[] args) {
        SpringApplication.run(InstaCommentApp.class, args);
    }


}