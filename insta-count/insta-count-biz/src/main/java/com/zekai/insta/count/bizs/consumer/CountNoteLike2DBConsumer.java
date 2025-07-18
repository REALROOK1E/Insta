package com.zekai.insta.count.bizs.consumer;
import com.google.common.util.concurrent.RateLimiter;

import com.zekai.framework.common.util.JsonUtils;
import com.zekai.insta.count.bizs.constant.MQConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import cn.hutool.core.collection.CollUtil;

import java.util.Map;

/**
 * @author: ZeKai
 * @date: 2025/7/18
 * @description: 使用 Guava 的 RateLimiter 限流器， 在 MQ 消费者类中进行流量削峰；
 * 将消息体中的字符串转换为 Map<Long, Integer> 字典；
 * 如果字典不为空，调用 insertOrUpdateLikeTotalByNoteId() 方法，查询数据库 t_note_count 笔记计数表中，该篇笔记的计数记录是否存在，若不存在，则插入；若记录已存在，则直接更新，执行累加或累减操作；
 **/
@Component
@RocketMQMessageListener(consumerGroup = "insta_group_" + MQConstants.TOPIC_COUNT_NOTE_LIKE_2_DB, // Group 组
        topic = MQConstants.TOPIC_COUNT_NOTE_LIKE_2_DB // 主题 Topic
)
@Slf4j
public class CountNoteLike2DBConsumer implements RocketMQListener<String> {

//    @Resource
//    private NoteCountDOMapper noteCountDOMapper;

    // 每秒创建 5000 个令牌
    private RateLimiter rateLimiter = RateLimiter.create(5000);

    @Override
    public void onMessage(String body) {
        // 流量削峰：通过获取令牌，如果没有令牌可用，将阻塞，直到获得
        rateLimiter.acquire();

        log.info("## 消费到了 MQ 【计数: 笔记点赞数入库】, {}...", body);

//        Map<Long, Integer> countMap = null;
//        try {
//            countMap = JsonUtils.parseMap(body, Long.class, Integer.class);
//        } catch (Exception e) {
//            log.error("## 解析 JSON 字符串异常", e);
//        }

//        if (CollUtil.isNotEmpty(countMap)) {
//            // 判断数据库中 t_note_count 表，若笔记计数记录不存在，则插入；若记录已存在，则直接更新
//            countMap.forEach((k, v) -> noteCountDOMapper.insertOrUpdateLikeTotalByNoteId(v, k));
//        }
    }

}

