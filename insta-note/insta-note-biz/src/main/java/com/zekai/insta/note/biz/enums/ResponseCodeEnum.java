package com.zekai.insta.note.biz.enums;

import com.zekai.framework.common.Exception.ExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: ZeKai
 * @date: 2025/7/10
 * @description:
 **/
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements ExceptionInterface {

    // ----------- 通用异常状态码 -----------
    SYSTEM_ERROR("NOTE-10000", "出错啦，后台小哥正在努力修复中..."),
    PARAM_NOT_VALID("NOTE-10001", "参数错误"),
    NOTE_TYPE_ERROR("NOTE-20000", "未知的笔记类型"),
    NOTE_PUBLISH_FAIL("NOTE-20001", "笔记发布失败"),
    NOTE_NOT_FOUND("NOTE-20002", "笔记不存在"),
    NOTE_PRIVATE("NOTE-20003", "作者已将该笔记设置为仅自己可见"),
    NOTE_UPDATE_FAIL("NOTE-20004", "笔记更新失败"),
    TOPIC_NOT_FOUND("NOTE-20005","没找到标题" ),
    NOTE_ALREADY_LIKED("NOTE-20008", "您已经点赞过该笔记")
            ;

    // 异常码
    private final String errorCode;
    // 错误信息
    private final String errorMessage;

    /**
     * @return
     */
    @Override
    public String getCode() {
        return "";
    }

    /**
     * @return
     */
    @Override
    public String getMsg() {
        return "";
    }
}
