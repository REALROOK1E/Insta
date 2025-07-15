package com.zekai.insta.note.biz.constant;

/**
 * @author: ZeKai
 * @date: 2025/7/13
 * @description:
 **/
public class RedisKeyConstants {
    public static final String NOTE_DETAIL_KEY = "note:detail:";


    /**
     * 构建完整的笔记详情 KEY
     * @param noteId
     * @return
     */
    public static String buildNoteDetailKey(Long noteId) {
        return NOTE_DETAIL_KEY + noteId;
    }
}
