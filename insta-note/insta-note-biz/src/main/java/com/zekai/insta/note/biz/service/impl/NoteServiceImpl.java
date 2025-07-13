package com.zekai.insta.note.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.google.common.base.Preconditions;
import com.zekai.framework.common.Exception.BizException;
import com.zekai.framework.common.response.Response;
import com.zekai.insta.context.holder.LoginUserContextHolder;
import com.zekai.insta.note.biz.domain.dataobject.NoteDO;
import com.zekai.insta.note.biz.domain.mapper.NoteDOMapper;
import com.zekai.insta.note.biz.domain.mapper.TopicDOMapper;
import com.zekai.insta.note.biz.enums.NoteStatusEnum;
import com.zekai.insta.note.biz.enums.NoteTypeEnum;
import com.zekai.insta.note.biz.enums.NoteVisibleEnum;
import com.zekai.insta.note.biz.enums.ResponseCodeEnum;
import com.zekai.insta.note.biz.model.vo.PublishNoteReqVO;
import com.zekai.insta.note.biz.rpc.DistributedIdGeneratorRpcService;
import com.zekai.insta.note.biz.rpc.KeyValueRpcService;
import com.zekai.insta.note.biz.service.NoteService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
/**
 * @author: ZeKai
 * @date: 2025/7/12
 * @description: /*根据笔记类型，获取对应的枚举类，判断笔记类型是否合法，若不合法，抛出业务异常；
 * 根据不同的笔记类型；
 * 如果是图文，判断对应的参数是否合法，若都合法，将图片链接拼接成字符串，以逗号分隔；
 * 如果是视频，判断视频连接不能为空；
 * 调用分布式 ID 生成服务，生成笔记 ID；
 * 判断笔记内容是否为空，若不为空，则生成笔记内容 UUID, 并调用 KV 键值服务存储笔记内容；
 * 根据返参判断内容是否保存成功，若失败，抛出业务异常；
 * 判断话题 ID 是否为空，若不为空，查询对应的话题名称；
 * 构建 NoteDO 数据库实体类，将笔记数据入库，同时对 insert() 方法捕获异常；
 * 由于笔记元数据与内容分散在不同的数据库中，传统的事务无法使用。这里的小技巧是，将笔记内容先存储，确认成功后，在插入笔记元数据，如果插入笔记元数据失败了，再调用 KV 键值服务，将笔记内容删除，以保证数据一致性。
 **/
@Service
@Slf4j
public class NoteServiceImpl implements NoteService {

    @Resource
    private NoteDOMapper noteDOMapper;
    @Resource
    private TopicDOMapper topicDOMapper;
    @Resource
    private DistributedIdGeneratorRpcService distributedIdGeneratorRpcService;
    @Resource
    private KeyValueRpcService keyValueRpcService;


    /**
     * 笔记发布
     *
     * @param publishNoteReqVO
     * @return
     */
    @Override
    public Response<?> publishNote(PublishNoteReqVO publishNoteReqVO) {
        // 笔记类型
        Integer type = publishNoteReqVO.getType();

        // 获取对应类型的枚举
        NoteTypeEnum noteTypeEnum = NoteTypeEnum.valueOf(type);

        // 若非图文、视频，抛出业务业务异常
        if (Objects.isNull(noteTypeEnum)) {
            throw new BizException(ResponseCodeEnum.NOTE_TYPE_ERROR);
        }

        String imgUris = null;
        // 笔记内容是否为空，默认值为 true，即空
        Boolean isContentEmpty = true;
        String videoUri = null;
        switch (noteTypeEnum) {
            case IMAGE_TEXT: // 图文笔记
                List<String> imgUriList = publishNoteReqVO.getImgUris();
                // 校验图片是否为空
                Preconditions.checkArgument(CollUtil.isNotEmpty(imgUriList), "笔记图片不能为空");
                // 校验图片数量
                Preconditions.checkArgument(imgUriList.size() <= 8, "笔记图片不能多于 8 张");
                // 将图片链接拼接，以逗号分隔
                imgUris = StringUtils.join(imgUriList, ",");

                break;
            case VIDEO: // 视频笔记
                videoUri = publishNoteReqVO.getVideoUri();
                // 校验视频链接是否为空
                Preconditions.checkArgument(StringUtils.isNotBlank(videoUri), "笔记视频不能为空");
                break;
            default:
                break;
        }

        // RPC: 调用分布式 ID 生成服务，生成笔记 ID
        String snowflakeIdId = distributedIdGeneratorRpcService.getSegmentId();
        // 笔记内容 UUID
        String contentUuid = null;

        // 笔记内容
        String content = publishNoteReqVO.getContent();

        // 若用户填写了笔记内容
        if (StringUtils.isNotBlank(content)) {
            // 内容是否为空，置为 false，即不为空
            isContentEmpty = false;
            // 生成笔记内容 UUID
            contentUuid = UUID.randomUUID().toString();
            // RPC: 调用 KV 键值服务，存储短文本
            boolean isSavedSuccess = keyValueRpcService.saveNoteContent(contentUuid, content);
            // 若存储失败，抛出业务异常，提示用户发布笔记失败
            if (!isSavedSuccess) {
                throw new BizException(ResponseCodeEnum.NOTE_PUBLISH_FAIL);
            }
        }

        // 话题
        Long topicId = publishNoteReqVO.getTopicId();
        String topicName = null;
        if (Objects.nonNull(topicId)) {
            // 获取话题名称
            topicName = topicDOMapper.selectNameByPrimaryKey(topicId);
        }

        // 发布者用户 ID
        Long creatorId = LoginUserContextHolder.getUserId();

        // 构建笔记 DO 对象
        NoteDO noteDO = NoteDO.builder()
                .id(Long.valueOf(snowflakeIdId))
                .isContentEmpty(isContentEmpty)
                .creatorId(creatorId)
                .imgUris(imgUris)
                .title(publishNoteReqVO.getTitle())
                .topicId(publishNoteReqVO.getTopicId())
                .topicName(topicName)
                .type(type)
                .visible(NoteVisibleEnum.PUBLIC.getCode())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .status(NoteStatusEnum.NORMAL.getCode())
                .isTop(Boolean.FALSE)
                .videoUri(videoUri)
                .contentUuid(contentUuid)
                .build();

        try {
            // 笔记入库存储
            noteDOMapper.insert(noteDO);
        } catch (Exception e) {
            log.error("==> 笔记存储失败", e);

            // RPC: 笔记保存失败，则删除笔记内容
            if (StringUtils.isNotBlank(contentUuid)) {
                keyValueRpcService.deleteNoteContent(contentUuid);
            }
        }

        return Response.success();
    }


}
