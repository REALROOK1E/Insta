package com.zekai.insta.note.biz.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/**
 * @author: ZeKai
 * @date: 2025/7/18
 * @description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeUnlikeNoteMqDTO {

    private Long userId;

    private Long noteId;

    /**
     * 0: 取消点赞， 1：点赞
     */
    private Integer type;

    private LocalDateTime createTime;
}
