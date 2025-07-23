package com.zekai.insta.start.util;

public class NumberUtils {
    /**
     * 格式化数字为字符串，支持 null 安全处理
     * @param number 数字对象
     * @return 格式化后的字符串，null 返回 "0"
     */
    public static String formatNumberString(Number number) {
        if (number == null) {
            return "0";
        }
        return String.valueOf(number);
    }
}

