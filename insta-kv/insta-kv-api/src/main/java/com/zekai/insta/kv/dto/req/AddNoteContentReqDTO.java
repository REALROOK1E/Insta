package com.zekai.insta.kv.dto.req;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author: ZeKai
 * @date: 2025/7/8
 * @description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddNoteContentReqDTO {

    @NotNull(message = "笔记 ID 不能为空")
    private Long noteId;

    @NotBlank(message = "笔记内容不能为空")
    private String content;

}