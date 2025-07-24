package com.zekai.comment.biz.service.impl;

import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.shaded.com.google.common.base.Preconditions;
import com.zekai.comment.biz.constant.MQConstants;
import com.zekai.comment.biz.model.dto.PublishCommentMqDTO;
import com.zekai.comment.biz.model.vo.PublishCommentReqVO;
import com.zekai.comment.biz.retry.SendMqRetryHelper;
import com.zekai.framework.common.response.Response;
import com.zekai.framework.common.util.JsonUtils;
import com.zekai.insta.context.holder.LoginUserContextHolder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import com.zekai.comment.biz.service.CommentService;

import java.time.LocalDateTime;

/**
 * @author Zekai
 * @date 2025/7/24
 * @Descripson :
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private SendMqRetryHelper sendMqRetryHelper;
    /**
     * 发布评论
     *
     * @param publishCommentReqVO
     * @return
     */
    @Override
    public Response<?> publishComment(PublishCommentReqVO publishCommentReqVO) {


        // 评论正文
        String content = publishCommentReqVO.getContent();
        // 附近图片
        String imageUrl = publishCommentReqVO.getImageUrl();

        // 评论内容和图片不能同时为空
        Preconditions.checkArgument(StringUtils.isNotBlank(content) || StringUtils.isNotBlank(imageUrl),
                "评论正文和图片不能同时为空");
        // 发布者 ID
        Long creatorId = LoginUserContextHolder.getUserId();

        // 发送 MQ
        // 构建消息体 DTO
        PublishCommentMqDTO publishCommentMqDTO = PublishCommentMqDTO.builder()
                .noteId(publishCommentReqVO.getNoteId())
                .content(content)
                .imageUrl(imageUrl)
                .replyCommentId(publishCommentReqVO.getReplyCommentId())
                .createTime(LocalDateTime.now())
                .creatorId(creatorId)
                .build();

        // 构建消息对象，并将 DTO 转成 Json 字符串设置到消息体中
        Message<String> message = MessageBuilder.withPayload(JsonUtils.toJsonString(publishCommentMqDTO))
                .build();

        // 发送 MQ (包含重试机制)
        sendMqRetryHelper.asyncSend(MQConstants.TOPIC_PUBLISH_COMMENT, JsonUtils.toJsonString(publishCommentMqDTO));

        return Response.success();

    }
}
