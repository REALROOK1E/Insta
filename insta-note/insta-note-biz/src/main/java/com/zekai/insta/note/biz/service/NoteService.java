package com.zekai.insta.note.biz.service;

import com.zekai.framework.common.response.Response;
import com.zekai.insta.note.biz.model.vo.FindNoteDetailReqVO;
import com.zekai.insta.note.biz.model.vo.FindNoteDetailRspVO;
import com.zekai.insta.note.biz.model.vo.PublishNoteReqVO;
import com.zekai.insta.note.biz.model.vo.UpdateNoteReqVO;

import java.util.concurrent.ExecutionException;

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

    Response<FindNoteDetailRspVO> findNoteDetail(FindNoteDetailReqVO findNoteDetailReqVO) throws ExecutionException, InterruptedException;

    Response<?> updateNote(UpdateNoteReqVO updateNoteReqVO);
    /**
     * 删除本地笔记缓存
     * @param noteId
     */
    void deleteNoteLocalCache(Long noteId);
}
