package com.zekai.insta.user.relation.biz.consumer;

import com.zekai.insta.user.relation.biz.constant.MQConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.apache.rocketmq.common.message.Message;
/**
 * @author: ZeKai
 * @date: 2025/7/17
 * @description:
 **/
@Component
@RocketMQMessageListener(consumerGroup = "insta_group", // Group 组
        topic = MQConstants.TOPIC_FOLLOW_OR_UNFOLLOW // 消费的 Topic 主题
)
@Slf4j
public class FollowUnfollowConsumer implements RocketMQListener<Message> {

    @Override
    public void onMessage(Message message) {
        // 消息体
        String bodyJsonStr = new String(message.getBody());
        // 标签
        String tags = message.getTags();

        log.info("==> FollowUnfollowConsumer 消费了消息 {}, tags: {}", bodyJsonStr, tags);
    }

}
