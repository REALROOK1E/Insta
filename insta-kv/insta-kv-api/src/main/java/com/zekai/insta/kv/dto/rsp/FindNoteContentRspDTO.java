package com.zekai.insta.kv.dto.rsp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
/**
 * @author: ZeKai
 * @date: 2025/7/8
 * @description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindNoteContentRspDTO {

    /**
     * 笔记 ID
     */
    private UUID noteId;

    /**
     * 笔记内容
     */
    private String content;

}