package com.zekai.insta.kv.dto.req;
import jakarta.validation.constraints.NotBlank;
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
public class FindNoteContentReqDTO {

    @NotBlank(message = "笔记 ID 不能为空")
    private String noteId;

}