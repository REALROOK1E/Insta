package com.zekai.insta.count.bizs.consumer;
import com.zekai.framework.common.util.JsonUtils;
import com.zekai.insta.count.bizs.constant.MQConstants;
import com.zekai.insta.count.bizs.constant.RedisKeyConstants;
import com.zekai.insta.count.bizs.dto.NoteOperateMqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: ZeKai
 * @date: 2025/7/18
 * @description: 这里大摆烂了，直接好多都没写，理解一下意思
 * 由于笔记发布、删除计数，代码逻辑基本一致，唯一区别的是，笔记发布需要执行笔记总数 +1 操作，删除执行 -1 操作，所以重构了一个 handleTagMessage() 方法来统一处理，逻辑如下：
 *
 * 将消息体 Json 字符串解析为 DTO 实体类，获取笔记发布者 ID；
 * 判断 Redis 中，笔记发布者对应的 Hash 是否初始化完成，若已初始化，则执行更新操作；
 * 再对 t_user_count 表中的 note_total 笔记发布数执行更新操作；
 **/
@Component
@RocketMQMessageListener(consumerGroup = "insta_group_" + MQConstants.TOPIC_NOTE_OPERATE, // Group 组
        topic = MQConstants.TOPIC_NOTE_OPERATE // 主题 Topic
)
@Slf4j
public class CountNotePublishConsumer implements RocketMQListener<Message> {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(Message message) {
        // 消息体
        String bodyJsonStr = new String(message.getBody());
        // 标签
        String tags = message.getTags();

        log.info("==> CountNotePublishConsumer 消费了消息 {}, tags: {}", bodyJsonStr, tags);

        // 根据 MQ 标签，判断笔记操作类型
        if (Objects.equals(tags, MQConstants.TAG_NOTE_PUBLISH)) { // 笔记发布
            handleNotePublishTagMessage(bodyJsonStr);
        } else if (Objects.equals(tags, MQConstants.TAG_NOTE_DELETE)) { // 笔记删除
         //   handleNoteDeleteTagMessage(bodyJsonStr);
        }
    }

    /**
     * 笔记发布、删除
     * @param bodyJsonStr
     */
    private void handleTagMessage(String bodyJsonStr, long count) {
        // 消息体 JSON 字符串转 DTO
        NoteOperateMqDTO noteOperateMqDTO = JsonUtils.parseObject(bodyJsonStr, NoteOperateMqDTO.class);

        if (Objects.isNull(noteOperateMqDTO)) return;

        // 笔记发布者 ID
        Long creatorId = noteOperateMqDTO.getCreatorId();

        // 更新 Redis 中用户维度的计数 Hash
        String countUserRedisKey = RedisKeyConstants.buildCountUserKey(creatorId);
        // 判断 Redis 中 Hash 是否存在
        boolean isCountUserExisted = redisTemplate.hasKey(countUserRedisKey);

        // 若存在才会更新
        // (因为缓存设有过期时间，考虑到过期后，缓存会被删除，这里需要判断一下，存在才会去更新，而初始化工作放在查询计数来做)
        if (isCountUserExisted) {
            // 对目标用户 Hash 中的笔记发布总数，进行加减操作
            redisTemplate.opsForHash().increment(countUserRedisKey, RedisKeyConstants.FIELD_NOTE_TOTAL, count);
        }

        // 更新 t_user_count 表
      //  userCountDOMapper.insertOrUpdateNoteTotalByUserId(count, creatorId);
    }

    /**
     * 笔记发布
     * @param bodyJsonStr
     */
    private void handleNotePublishTagMessage(String bodyJsonStr) {
    }

}
