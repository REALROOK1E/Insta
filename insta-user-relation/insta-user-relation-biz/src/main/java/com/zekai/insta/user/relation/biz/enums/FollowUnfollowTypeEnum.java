package com.zekai.insta.user.relation.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * @author: ZeKai
 * @date: 2025/7/18
 * @description:
 **/
@Getter
@AllArgsConstructor
public enum FollowUnfollowTypeEnum {
    // 关注
    FOLLOW(1),
    // 取关
    UNFOLLOW(0),
    ;

    private final Integer code;

}
