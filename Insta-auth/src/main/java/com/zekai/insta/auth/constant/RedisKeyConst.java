package com.zekai.insta.auth.constant;

/**
 * @author: ZeKai
 * @date: 2025/6/20
 * @description: 常量类
 **/
public class RedisKeyConst {


    public static final String COMMON_USER_ROLE_ID = "不知道写啥";
    private static final String VERIFICATION_CODE_KEY_PREFIX = "verification_code:";
        private static final String USER_ROLES_KEY_PREFIX = "user:roles:";

        private static final String ROLE_PERMISSIONS_KEY_PREFIX = "role:permissions:";
        /**
         * 构建验证码 KEY
         * @param phone
         * @return
         */
        public static final String INSTA_ID_GENERATOR_KEY = "Insta.id.generator";

        public static String buildVerificationCodeKey(String phone) {
            return VERIFICATION_CODE_KEY_PREFIX + phone;
        }

         public static String buildUserRoleKey(Long userId) {
        return USER_ROLES_KEY_PREFIX + userId;
    }

        public static String buildRolePermissionsKey(Long roleId) {
        return ROLE_PERMISSIONS_KEY_PREFIX + roleId;
    }

}
