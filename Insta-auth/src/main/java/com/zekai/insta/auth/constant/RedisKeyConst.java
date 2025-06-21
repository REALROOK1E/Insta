package com.zekai.insta.auth.constant;

/**
 * @author: ZeKai
 * @date: 2025/6/20
 * @description: 常量类，用于拼接短信
 **/
public class RedisKeyConst {

        private static final String VERIFICATION_CODE_KEY_PREFIX = "verification_code:";
        private static final String USER_ROLES_KEY_PREFIX = "user:roles:";
        /**
         * 构建验证码 KEY
         * @param phone
         * @return
         */
        public static final String INSTA_ID_GENERATOR_KEY = "Insta_id_generator";

        public static String buildVerificationCodeKey(String phone) {
            return VERIFICATION_CODE_KEY_PREFIX + phone;
        }
    public static String buildUserRoleKey(String phone) {
        return USER_ROLES_KEY_PREFIX + phone;
    }

}
