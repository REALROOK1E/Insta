package com.zekai.insta.user.relation.biz.consumer;

import com.google.common.util.concurrent.RateLimiter;
import com.zekai.framework.common.util.JsonUtils;
import com.zekai.insta.user.relation.biz.constant.MQConstants;
import com.zekai.insta.user.relation.biz.constant.RedisKeyConstants;
import com.zekai.insta.user.relation.biz.domain.mapper.FansDOMapper;
import com.zekai.insta.user.relation.biz.domain.mapper.FollowingDOMapper;
import com.zekai.insta.user.relation.biz.model.dto.UnfollowUserMqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.apache.rocketmq.common.message.Message;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.Objects;

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
    private RedisTemplate<String, Object> redisTemplate;
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
        if (Objects.equals(tags, MQConstants.TAG_FOLLOW)) { // 关注
            handleUnfollowTagMessage(bodyJsonStr);
        } else if (Objects.equals(tags, MQConstants.TAG_UNFOLLOW)) { // 取关
            handleUnfollowTagMessage(bodyJsonStr);
        }

    }
    /**
     * 取关
     * @param bodyJsonStr
     */
    private void handleUnfollowTagMessage(String bodyJsonStr) {
        // 将消息体 Json 字符串转为 DTO 对象
        UnfollowUserMqDTO unfollowUserMqDTO = JsonUtils.parseObject(bodyJsonStr, UnfollowUserMqDTO.class);

        // 判空
        if (Objects.isNull(unfollowUserMqDTO)) return;

        Long userId = unfollowUserMqDTO.getUserId();
        Long unfollowUserId = unfollowUserMqDTO.getUnfollowUserId();
        LocalDateTime createTime = unfollowUserMqDTO.getCreateTime();

        // 编程式提交事务
        boolean isSuccess = Boolean.TRUE.equals(transactionTemplate.execute(status -> {
            try {
                // 取关成功需要删除数据库两条记录
                // 关注表：一条记录
                int count = followingDOMapper.deleteByUserIdAndFollowingUserId(userId, unfollowUserId);
                // 粉丝表：一条记录
                if (count > 0) {
                    fansDOMapper.deleteByUserIdAndFansUserId(unfollowUserId, userId);
                }
                return true;
            } catch (Exception ex) {
                status.setRollbackOnly(); // 标记事务为回滚
                log.error("", ex);
            }
            return false;
        }));

        // 若数据库删除成功，更新 Redis，将自己从被取注用户的 ZSet 粉丝列表删除
        if (isSuccess) {
            // 被取关用户的粉丝列表 Redis Key
            String fansRedisKey = RedisKeyConstants.buildUserFansKey(unfollowUserId);
            // 删除指定粉丝
            redisTemplate.opsForZSet().remove(fansRedisKey, userId);
        }

    }
}
