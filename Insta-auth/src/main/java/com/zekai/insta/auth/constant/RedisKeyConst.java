package com.zekai.insta.auth.constant;

/**
 * @author: ZeKai
 * @date: 2025/6/20
 * @description: 常量类，用于拼接短信
 **/
public class RedisKeyConst {
    public class RedisKeyConstants {


        private static final String VERIFICATION_CODE_KEY_PREFIX = "verification_code:";

        /**
         * 构建验证码 KEY
         * @param phone
         * @return
         */

        public static String buildVerificationCodeKey(String phone) {
            return VERIFICATION_CODE_KEY_PREFIX + phone;
        }
    }
}
