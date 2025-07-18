package com.zekai.insta.kv.dto.req;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * @author: ZeKai
 * @date: 2025/7/8
 * @description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class AddNoteContentReqDTO {

    @NotBlank(message = "笔记内容 UUID 不能为空")
    private String uuid;

    @NotBlank(message = "笔记内容不能为空")
    private String content;

}
