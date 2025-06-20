package com.zekai.insta.auth;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author: ZeKai
 * @date: 2025/6/21
 * @description:
 **/
@SpringBootTest
@Slf4j
public class ThreadPoolTest {
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 测试线程池
     */
    @Test
    void testSubmit() {
        threadPoolTaskExecutor.submit(() -> log.info("Asynchronous thread"));
    }
}
