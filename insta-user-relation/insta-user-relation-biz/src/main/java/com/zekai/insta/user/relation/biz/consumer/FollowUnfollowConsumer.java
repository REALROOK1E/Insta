package com.zekai.insta.user.relation.biz.consumer;

import com.google.common.util.concurrent.RateLimiter;
import com.zekai.insta.user.relation.biz.constant.MQConstants;
import com.zekai.insta.user.relation.biz.domain.mapper.FansDOMapper;
import com.zekai.insta.user.relation.biz.domain.mapper.FollowingDOMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.apache.rocketmq.common.message.Message;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author: ZeKai
 * @date: 2025/7/17
 * @description: 令牌桶
 **/
@Component
@RocketMQMessageListener(consumerGroup = "insta_group", // Group 组
        topic = MQConstants.TOPIC_FOLLOW_OR_UNFOLLOW // 消费的 Topic 主题
)
@Slf4j
public class FollowUnfollowConsumer implements RocketMQListener<Message> {

    @Resource
    private FollowingDOMapper followingDOMapper;
    @Resource
    private FansDOMapper fansDOMapper;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private RateLimiter rateLimiter;
    // 每秒创建 5000 个令牌

    @Override
    public void onMessage(Message message) {

        // 令牌桶限流
        rateLimiter.acquire();
        // 消息体
        String bodyJsonStr = new String(message.getBody());
        // 标签
        String tags = message.getTags();

        log.info("==> FollowUnfollowConsumer 消费了消息 {}, tags: {}", bodyJsonStr, tags);
    }

}
