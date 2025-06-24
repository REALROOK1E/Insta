package com.zekai.insta.auth;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: ZeKai
 * @date: 2025/6/20
 * @description:
 **/

@SpringBootTest
@Slf4j

public class RedisTest {

        @Resource
        private RedisTemplate<String, Object> redisTemplate;
        /**
         * set key value
         */

}
