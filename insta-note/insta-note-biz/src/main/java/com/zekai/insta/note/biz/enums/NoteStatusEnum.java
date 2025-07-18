package com.zekai.insta.note.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: ZeKai
 * @date: 2025/7/10
 * @description:
 **/
@Getter
@AllArgsConstructor
public enum NoteStatusEnum {

    BE_EXAMINE(0), // 待审核
    NORMAL(1), // 正常展示
    DELETED(2), // 被删除
    DOWNED(3), // 被下架
    ;

    private final Integer code;

}
