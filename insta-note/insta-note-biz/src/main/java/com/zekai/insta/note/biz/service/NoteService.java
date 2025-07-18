package com.zekai.insta.note.biz.service;

import com.zekai.framework.common.response.Response;
import com.zekai.insta.note.biz.model.vo.*;

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
    Response<?> deleteNote(DeleteNoteReqVO deleteNoteReqVO);

    Response<?> likeNote(LikeNoteReqVO likeNoteReqVO);
}
