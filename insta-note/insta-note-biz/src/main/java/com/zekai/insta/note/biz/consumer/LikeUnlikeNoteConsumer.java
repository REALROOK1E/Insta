package com.zekai.insta.note.biz.consumer;
import com.google.common.util.concurrent.RateLimiter;
import com.zekai.insta.note.biz.constant.MQConstants;
import com.zekai.insta.note.biz.domain.mapper.NoteDOMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
/**
 * @author: ZeKai
 * @date: 2025/7/18
 * @description:
 **/
@Component
@RocketMQMessageListener(consumerGroup = "insta_group_" + MQConstants.TOPIC_LIKE_OR_UNLIKE, // Group 组
        topic = MQConstants.TOPIC_LIKE_OR_UNLIKE, // 消费的主题 Topic
        consumeMode = ConsumeMode.ORDERLY // 设置为顺序消费模式
)
@Slf4j
public class LikeUnlikeNoteConsumer implements RocketMQListener<Message> {
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    // 每秒创建 5000 个令牌
    private RateLimiter rateLimiter = RateLimiter.create(5000);

    @Override
    public void onMessage(Message message) {
        // 流量削峰：通过获取令牌，如果没有令牌可用，将阻塞，直到获得
        rateLimiter.acquire();
        // 幂等性: 通过联合唯一索引保证

        // 消息体
        String bodyJsonStr = new String(message.getBody());
        // 标签
        String tags = message.getTags();

        log.info("==> LikeUnlikeNoteConsumer 消费了消息 {}, tags: {}", bodyJsonStr, tags);

        // 根据 MQ 标签，判断操作类型
        if (Objects.equals(tags, MQConstants.TAG_LIKE)) { // 点赞笔记
            handleLikeNoteTagMessage(bodyJsonStr);
        } else if (Objects.equals(tags, MQConstants.TAG_UNLIKE)) { // 取消点赞笔记
            handleUnlikeNoteTagMessage(bodyJsonStr);
        }
    }
    /**
     * 笔记点赞
     * @param bodyJsonStr
     */
    private void handleLikeNoteTagMessage(String bodyJsonStr) {

        int count=Integer.parseInt(Arrays.toString(bodyJsonStr.getBytes()));
        //这里用了假的数据
        // 模拟更新数据库的操作，实际应用中应调用服务层方法进行数据库操作
        if (count == 0) return;
        // 更新数据库成功后，发送计数 MQ
        org.springframework.messaging.Message<String> message = MessageBuilder.withPayload(bodyJsonStr)
                .build();
        // 异步发送 MQ 消息
        rocketMQTemplate.asyncSend(MQConstants.TOPIC_COUNT_NOTE_LIKE, message, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        log.info("==> 【计数: 笔记点赞】MQ 发送成功，SendResult: {}", sendResult);
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        log.error("==> 【计数: 笔记点赞】MQ 发送异常: ", throwable);
                    }
                });
    }

    /**
     * 笔记取消点赞
     * @param bodyJsonStr
     */
    private void handleUnlikeNoteTagMessage(String bodyJsonStr) {
    }

}
