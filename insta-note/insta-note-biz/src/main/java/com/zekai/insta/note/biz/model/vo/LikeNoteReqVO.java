package com.zekai.insta.note.biz.model.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author: ZeKai
 * @date: 2025/7/18
 * @description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeNoteReqVO {
   Long Id;
}
