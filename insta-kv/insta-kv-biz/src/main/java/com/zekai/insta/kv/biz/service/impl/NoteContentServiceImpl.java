package com.zekai.insta.kv.biz.service.impl;

import com.zekai.framework.common.Exception.BizException;
import com.zekai.framework.common.response.Response;
import com.zekai.insta.kv.biz.domain.dataobject.NoteContentDO;
import com.zekai.insta.kv.biz.domain.repository.NoteContentRepository;
import com.zekai.insta.kv.biz.service.NoteContentService;
import com.zekai.insta.kv.dto.req.AddNoteContentReqDTO;
import com.zekai.insta.kv.dto.req.DeleteNoteContentReqDTO;
import com.zekai.insta.kv.dto.req.FindNoteContentReqDTO;
import com.zekai.insta.kv.dto.rsp.FindNoteContentRspDTO;
import com.zekai.insta.kv.enums.ResponseCodeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author: ZeKai
 * @date: 2025/7/8
 * @description: 把帖子保存到cassanrda里面
 **/
@Service
@Slf4j
public class NoteContentServiceImpl implements NoteContentService {

    @Resource
    private NoteContentRepository noteContentRepository;


    @Override
    public Response<?> addNoteContent(AddNoteContentReqDTO addNoteContentReqDTO) {
        // 笔记 ID
        Long noteId = addNoteContentReqDTO.getNoteId();
        // 笔记内容
        String content = addNoteContentReqDTO.getContent();

        // 构建数据库 DO 实体类
        NoteContentDO nodeContent = NoteContentDO.builder()
                .id(UUID.randomUUID()) // TODO: 暂时用 UUID, 目的是为了下一章讲解压测，不用动态传笔记 ID。后续改为笔记服务传过来的笔记 ID
                .content(content)
                .build();

        // 插入数据
        noteContentRepository.save(nodeContent);

        return Response.success();
    }

    @Override
    public Response<FindNoteContentRspDTO> findNoteContent(FindNoteContentReqDTO findNoteContentReqDTO) {
        // 笔记 ID
        String noteId = findNoteContentReqDTO.getNoteId();
        // 根据笔记 ID 查询笔记内容
        Optional<NoteContentDO> optional = noteContentRepository.findById(UUID.fromString(noteId));

        // 若笔记内容不存在
        if (!optional.isPresent()) {
            throw new BizException(ResponseCodeEnum.NOTE_CONTENT_NOT_FOUND);
        }

        NoteContentDO noteContentDO = optional.get();
        // 构建返参 DTO
        FindNoteContentRspDTO findNoteContentRspDTO = FindNoteContentRspDTO.builder()
                .noteId(noteContentDO.getId())
                .content(noteContentDO.getContent())
                .build();

        return Response.success(findNoteContentRspDTO);
    }

    @Override
    public Response<?> deleteNoteContent(DeleteNoteContentReqDTO deleteNoteContentReqDTO) {
        // 笔记 ID
        String noteId = deleteNoteContentReqDTO.getNoteId();
        // 删除笔记内容
        noteContentRepository.deleteById(UUID.fromString(noteId));

        return Response.success();
    }



}