package com.zekai.insta.count.bizs.consumer;

import com.google.common.util.concurrent.RateLimiter;
import com.zekai.framework.common.util.JsonUtils;
import com.zekai.insta.count.bizs.constant.MQConstants;
import com.zekai.insta.count.bizs.domain.mapper.UserCountDOMapper;
import com.zekai.insta.count.bizs.dto.CountFollowUnfollowMqDTO;
import com.zekai.insta.count.bizs.enums.FollowUnfollowTypeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: ZeKai
 * @date: 2025/7/18
 * @description: 将消息体 body 解析为 CountFollowUnfollowMqDTO 实体类；
 * 根据操作类型，计算 count 值，关注 +1， 取关 -1；
 * 执行 insertOrUpdateFollowingTotalByUserId() 方法，判断数据库中，若原用户的记录不存在，则插入；若记录已存在，则直接更新；
 **/
@Component
@RocketMQMessageListener(consumerGroup = "insta_group_" + MQConstants.TOPIC_COUNT_FOLLOWING_2_DB, // Group 组
        topic = MQConstants.TOPIC_COUNT_FOLLOWING_2_DB // 主题 Topic
)
@Slf4j
public class CountFollowing2DBConsumer implements RocketMQListener<String> {

    @Resource
    private UserCountDOMapper userCountDOMapper;

    // 每秒创建 5000 个令牌
    private RateLimiter rateLimiter = RateLimiter.create(5000);

    @Override
    public void onMessage(String body) {
        // 流量削峰：通过获取令牌，如果没有令牌可用，将阻塞，直到获得
        rateLimiter.acquire();

        log.info("## 消费到了 MQ 【计数: 关注数入库】, {}...", body);

        if (StringUtils.isBlank(body)) return;

        CountFollowUnfollowMqDTO countFollowUnfollowMqDTO = JsonUtils.parseObject(body, CountFollowUnfollowMqDTO.class);

        // 操作类型：关注 or 取关
        Integer type = countFollowUnfollowMqDTO.getType();
        // 原用户ID
        Long userId = countFollowUnfollowMqDTO.getUserId();

        // 关注数：关注 +1， 取关 -1
        int count = Objects.equals(type, FollowUnfollowTypeEnum.FOLLOW.getCode()) ? 1 : -1;
        // 判断数据库中，若原用户的记录不存在，则插入；若记录已存在，则直接更新
        userCountDOMapper.insertOrUpdateFollowingTotalByUserId(count, userId);
    }

}
