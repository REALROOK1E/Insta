package com.zekai.insta.user.biz.constant;

/**
 * @author: ZeKai
 * @date: 2025/6/20
 * @description: 常量类
 **/
public class RedisKeyConst {


    private static final String USER_INFO_KEY_PREFIX = "user:info:";
        private static final String USER_ROLES_KEY_PREFIX = "user:roles:";

        private static final String ROLE_PERMISSIONS_KEY_PREFIX = "role:permissions:";
        /**
         * 构建验证码 KEY
         * @param phone
         * @return
         */
        public static final String INSTA_ID_GENERATOR_KEY = "Insta.id.generator";

         public static String buildUserRoleKey(Long userId) {
        return USER_ROLES_KEY_PREFIX + userId;
    }

    public static String buildRolePermissionsKey(String roleKey) {
        return ROLE_PERMISSIONS_KEY_PREFIX + roleKey;
    }

    public static String buildUserInfoKey(Long userId) {
        return USER_INFO_KEY_PREFIX + userId;
    }
}
