package com.zekai.insta.note.biz.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * @author: ZeKai
 * @date: 2025/7/18
 * @description:
 **/
@Getter
@AllArgsConstructor
public enum LikeUnlikeNoteTypeEnum {
    // 点赞
    LIKE(1),
    // 取消点赞
    UNLIKE(0),
    ;

    private final Integer code;

}
