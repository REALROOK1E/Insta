package com.zekai.framework.common.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author: ZeKai
 * @date: 2025/7/17
 * @description:
 **/
public class DateUtils {
    /**
     * LocalDateTime 转时间戳
     *
     * @param localDateTime
     * @return
     */
    public static long localDateTime2Timestamp(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
