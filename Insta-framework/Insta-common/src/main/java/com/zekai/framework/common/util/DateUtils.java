package com.zekai.framework.common.util;

import com.zekai.framework.common.constants.DateConstants;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

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
    /**
     * LocalDateTime 转友好的相对时间字符串
     *
     * @param dateTime
     * @return 友好时间字符串，如“3分钟前”、“昨天 12:30”等
     */
    public static String formatRelativeTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        LocalDateTime now = LocalDateTime.now();
        long daysDiff = ChronoUnit.DAYS.between(dateTime, now);
        long hoursDiff = ChronoUnit.HOURS.between(dateTime, now);
        long minutesDiff = ChronoUnit.MINUTES.between(dateTime, now);

        if (daysDiff < 1) {  // 今天
            if (hoursDiff < 1) {
                return minutesDiff + "分钟前";
            } else {
                return hoursDiff + "小时前";
            }
        } else if (daysDiff == 1) {  // 昨天
            return "昨天 " + dateTime.format(DateConstants.DATE_FORMAT_H_M);
        } else if (daysDiff < 7) {  // 最近一周
            return daysDiff + "天前";
        } else if (dateTime.getYear() == now.getYear()) {  // 今年
            return dateTime.format(DateConstants.DATE_FORMAT_M_D);
        } else {  // 去年或更早
            return dateTime.format(DateConstants.DATE_FORMAT_Y_M_D);
        }
    }


    /**
     * LocalDateTime 转 String 字符串
     * @param time
     * @return
     */
    public static String localDateTime2String(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        long daysDiff = ChronoUnit.DAYS.between(time, now);
        long hoursDiff = ChronoUnit.HOURS.between(time, now);
        long minutesDiff = ChronoUnit.MINUTES.between(time, now);
        if (daysDiff < 1) {
            if (hoursDiff < 1) {
                return minutesDiff + "分钟前";
            } else {
                return hoursDiff + "小时前";
            }
        } else if (daysDiff == 1) {
            return "昨天 " + time.format(DateConstants.DATE_FORMAT_H_M);
        } else if (daysDiff < 7) {
            return daysDiff + "天前";
        } else if (time.getYear() == now.getYear()) {
            return time.format(DateConstants.DATE_FORMAT_M_D);
        } else {
            return time.format(DateConstants.DATE_FORMAT_Y_M_D);
        }
    }
}
