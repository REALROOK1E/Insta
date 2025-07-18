package com.zekai.insta.user.relation.biz.constant;

/**
 * @author: ZeKai
 * @date: 2025/7/17
 * @description:
 **/
public class RedisKeyConstants {
    /**
     * 关注列表 KEY 前缀
     */
    private static final String USER_FOLLOWING_KEY_PREFIX = "following:";

    /**
     * 构建关注列表完整的 KEY
     * @param userId
     * @return
     */
    public static String buildUserFollowingKey(Long userId) {
        return USER_FOLLOWING_KEY_PREFIX + userId;
    }

    public static String buildUserFansKey(Long unfollowUserId) {
        return "fans:" + unfollowUserId;
    }
}
