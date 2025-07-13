package com.zekai.insta.note.biz.service;

import com.zekai.framework.common.response.Response;
import com.zekai.insta.note.biz.model.vo.FindNoteDetailReqVO;
import com.zekai.insta.note.biz.model.vo.FindNoteDetailRspVO;
import com.zekai.insta.note.biz.model.vo.PublishNoteReqVO;

/**
 * @author: ZeKai
 * @date: 2025/7/12
 * @description:
 **/
public interface NoteService {

    /**
     * 笔记发布
     * @param publishNoteReqVO
     * @return
     */
    Response<?> publishNote(PublishNoteReqVO publishNoteReqVO);

    Response<FindNoteDetailRspVO> findNoteDetail(FindNoteDetailReqVO findNoteDetailReqVO);
}
