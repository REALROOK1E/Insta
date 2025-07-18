package com.zekai.insta.count.bizs.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

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

    public static FollowUnfollowTypeEnum valueOf(Integer code) {
        for (FollowUnfollowTypeEnum followUnfollowTypeEnum : FollowUnfollowTypeEnum.values()) {
            if (Objects.equals(code, followUnfollowTypeEnum.getCode())) {
                return followUnfollowTypeEnum;
            }
        }
        return null;
    }

}
