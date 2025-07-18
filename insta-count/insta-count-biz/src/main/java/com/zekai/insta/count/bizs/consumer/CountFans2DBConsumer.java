package com.zekai.insta.count.bizs.consumer;
import com.google.common.util.concurrent.RateLimiter;
import com.zekai.insta.count.bizs.constant.MQConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
/**
 * @author: ZeKai
 * @date: 2025/7/18
 * @description:
 **/
@Component
@RocketMQMessageListener(consumerGroup = "insta_group_" + MQConstants.TOPIC_COUNT_FANS_2_DB, // Group 组
        topic = MQConstants.TOPIC_COUNT_FANS_2_DB // 主题 Topic
)
@Slf4j
public class CountFans2DBConsumer implements RocketMQListener<String> {
    /**
     * 限流器，限制每秒最多处理 5000 条消息
     * 注意：RateLimiter.create(5000) 的参数是每秒的请求数
     */
    private RateLimiter rateLimiter = RateLimiter.create(5000);
    @Override
    public void onMessage(String body) {
        rateLimiter.acquire();

        log.info("## 消费到了 MQ 【计数: 粉丝数入库】, {}...", body);
    }

}
