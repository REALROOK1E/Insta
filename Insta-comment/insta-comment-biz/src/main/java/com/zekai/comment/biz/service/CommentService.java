package com.zekai.comment.biz.service;

import com.zekai.comment.biz.model.vo.PublishCommentReqVO;
import com.zekai.framework.common.response.Response;

public interface CommentService {

    /**
     * 发布评论
     * @param publishCommentReqVO
     * @return
     */
    Response<?> publishComment(PublishCommentReqVO publishCommentReqVO);
}
