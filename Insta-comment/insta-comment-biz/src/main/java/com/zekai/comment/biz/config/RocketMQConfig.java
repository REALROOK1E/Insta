package com.zekai.comment.biz.config;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
/**
 * @author Zekai
 * @date 2025/7/24
 * @Descripson :
 */

@Configuration
@Import(RocketMQAutoConfiguration .class)
public class RocketMQConfig {
}