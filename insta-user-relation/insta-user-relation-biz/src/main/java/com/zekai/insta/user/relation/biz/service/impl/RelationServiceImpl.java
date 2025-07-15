package com.zekai.insta.user.relation.biz.service.impl;

import com.zekai.framework.common.Exception.BizException;
import com.zekai.framework.common.response.Response;
import com.zekai.insta.context.holder.LoginUserContextHolder;
import com.zekai.insta.user.dto.resp.FindUserByIdRspDTO;
import com.zekai.insta.user.relation.biz.enums.ResponseCodeEnum;
import com.zekai.insta.user.relation.biz.model.vo.FollowUserReqVO;
import com.zekai.insta.user.relation.biz.rpc.UserRpcService;
import com.zekai.insta.user.relation.biz.service.RelationService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author: ZeKai
 * @date: 2025/7/15
 * @description:
 **/
@Service
@Slf4j
public class RelationServiceImpl implements RelationService {
    @Resource
    private UserRpcService userRpcService;
    /**
     * 关注用户
     *
     * @param followUserReqVO
     * @return
     */
    @Override
    public Response<?> follow(FollowUserReqVO followUserReqVO) {
        // 关注的用户 ID
        Long followUserId = followUserReqVO.getFollowUserId();

        // 当前登录的用户 ID
        Long userId = LoginUserContextHolder.getUserId();

        // 校验：无法关注自己
        if (Objects.equals(userId, followUserId)) {
            throw new BizException(ResponseCodeEnum.CANT_FOLLOW_YOUR_SELF);
        }

        // 校验关注的用户是否存在
        FindUserByIdRspDTO findUserByIdRspDTO = userRpcService.findById(followUserId);

        if (Objects.isNull(findUserByIdRspDTO)) {
            throw new BizException(ResponseCodeEnum.FOLLOW_USER_NOT_EXISTED);
        }

        // TODO: 校验关注数是否已经达到上限

        // TODO: 写入 Redis ZSET 关注列表

        // TODO: 发送 MQ

        return Response.success();
    }
}
