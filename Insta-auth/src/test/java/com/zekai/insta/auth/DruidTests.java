package com.zekai.insta.auth;

import com.alibaba.druid.filter.config.ConfigTools;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: ZeKai
 * @date: 2025/6/20
 * @description:
 **/
@SpringBootTest
@Slf4j
class DruidTests {

    //使用druid密码加密
    @Test
    @SneakyThrows
    void testEncodePassword() {
        String password = "lzk100207";
        String[] arr = ConfigTools.genKeyPair(512);

        log.info("privateKey: {}", arr[0]);

        log.info("publicKey: {}", arr[1]);

        // encode with private key
        String encodePassword = ConfigTools.encrypt(arr[0], password);
        log.info("password: {}", encodePassword);
    }

}