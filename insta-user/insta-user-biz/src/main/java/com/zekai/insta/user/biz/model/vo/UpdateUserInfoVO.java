package com.zekai.insta.user.biz.model.vo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
/**
 * @author: ZeKai
 * @date: 2025/6/29
 * @description:
 **/

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class UpdateUserInfoVO {

        /**
         * 头像
         */
        private MultipartFile avatar;

        /**
         * 昵称
         */
        private String nickname;

        /**
         * 小哈书 ID
         */
        private String instaId;

        /**
         * 性别
         */
        private Integer sex;

        /**
         * 生日
         */
        private LocalDate birthday;

        /**
         * 个人介绍
         */
        private String introduction;

        /**
         * 背景图
         */
        private MultipartFile backgroundImg;

    }


