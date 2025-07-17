package com.zekai.insta.user.relation.biz.service;

import com.zekai.framework.common.response.Response;
import com.zekai.insta.user.relation.biz.model.vo.FollowUserReqVO;
import com.zekai.insta.user.relation.biz.model.vo.UnfollowUserReqVO;

/**
 * @author: ZeKai
 * @date: 2025/7/15
 * @description:
 **/
public interface RelationService {

    /**
     * 关注用户
     * @param followUserReqVO
     * @return
     */
    Response<?> follow(FollowUserReqVO followUserReqVO);

    Response<?> unfollow(UnfollowUserReqVO unfollowUserReqVO);

}
