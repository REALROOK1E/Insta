package com.zekai.framework.common.constants;

import java.time.format.DateTimeFormatter;

/**
 * @author: ZeKai
 * @date: 2025/6/24
 * @description:
 **/
public interface Timeconsts {
    public interface DateConstants {
        /**
         * DateTimeFormatter：年-月-日 时：分：秒
         */
        DateTimeFormatter DATE_FORMAT_Y_M_D_H_M_S = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        /**
         * DateTimeFormatter：年-月-日
         */
        DateTimeFormatter DATE_FORMAT_Y_M_D = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        /**
         * DateTimeFormatter：时：分：秒
         */
        DateTimeFormatter DATE_FORMAT_H_M_S = DateTimeFormatter.ofPattern("HH:mm:ss");

        /**
         * DateTimeFormatter：年-月
         */
        DateTimeFormatter DATE_FORMAT_Y_M =  DateTimeFormatter.ofPattern("yyyy-MM");
    }

}
