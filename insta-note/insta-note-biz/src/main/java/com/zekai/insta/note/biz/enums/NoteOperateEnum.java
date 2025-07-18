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
public enum NoteOperateEnum {
    // 笔记发布
    PUBLISH(1),
    // 笔记删除
    DELETE(0),
    ;

    private final Integer code;

}
