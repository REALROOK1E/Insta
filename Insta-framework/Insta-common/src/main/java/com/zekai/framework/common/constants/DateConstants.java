package com.zekai.framework.common.constants;
import java.time.format.DateTimeFormatter;
public interface DateConstants {
    /**
     * DateTimeFormatter：月-日
     */
    DateTimeFormatter DATE_FORMAT_M_D = DateTimeFormatter.ofPattern("MM-dd");


    /**
     * DateTimeFormatter：时：分
     */
    DateTimeFormatter DATE_FORMAT_H_M = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter DATE_FORMAT_Y_M_D = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
