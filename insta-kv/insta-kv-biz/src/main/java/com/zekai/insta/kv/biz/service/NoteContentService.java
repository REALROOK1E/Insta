package com.zekai.insta.kv.biz.service;
import com.zekai.framework.common.response.Response;
import com.zekai.insta.kv.dto.req.AddNoteContentReqDTO;
import com.zekai.insta.kv.dto.req.DeleteNoteContentReqDTO;
import com.zekai.insta.kv.dto.req.FindNoteContentReqDTO;
import com.zekai.insta.kv.dto.rsp.FindNoteContentRspDTO;

/**
 * @author: ZeKai
 * @date: 2025/7/8
 * @description:
 **/
public interface NoteContentService {

    /**
     * 添加笔记内容
     *
     * @param addNoteContentReqDTO
     * @return
     */
    Response<?> addNoteContent(AddNoteContentReqDTO addNoteContentReqDTO);


    Response<?> deleteNoteContent(DeleteNoteContentReqDTO deleteNoteContentReqDTO);


    Response<FindNoteContentRspDTO> findNoteContent(FindNoteContentReqDTO findNoteContentReqDTO);
}