package com.zekai.insta.count.bizs.constant;

/**
 * @author: ZeKai
 * @date: 2025/7/18
 * @description:
 **/
public class RedisKeyConstants {

    public static final Object FIELD_NOTE_TOTAL = "noteTotal";
    /**
     * 用户维度计数 Key 前缀
     */
    private static final String COUNT_USER_KEY_PREFIX = "count:user:";

    /**
     * Hash Field: 粉丝总数
     */
    public static final String FIELD_FANS_TOTAL = "fansTotal";
    /**
     * Hash Field: 关注总数
     */
    public static final String FIELD_FOLLOWING_TOTAL = "followingTotal";
    /**
     * 笔记维度计数 Key 前缀
     */
    private static final String COUNT_NOTE_KEY_PREFIX = "count:note:";

    /**
     * Hash Field: 笔记点赞总数
     */
    public static final String FIELD_LIKE_TOTAL = "likeTotal";
    /**
     * 构建用户维度计数 Key
     * @param userId
     * @return
     */
    public static String buildCountUserKey(Long userId) {
        return COUNT_USER_KEY_PREFIX + userId;
    }
    public static String buildCountNoteKey(Long noteId) {
        return COUNT_NOTE_KEY_PREFIX + noteId;
    }

}
