package com.zekai.insta.user.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author: ZeKai
 * @date: 2025/6/29
 * @description:
 **/
@Getter
@AllArgsConstructor
public enum GenderEnum {

    WOMAN(0),
    MAN(1);

    private final Integer value;

    public static boolean isValid(Integer value) {
        for (GenderEnum loginTypeEnum : GenderEnum.values()) {
            if (Objects.equals(value, loginTypeEnum.getValue())) {
                return true;
            }
        }
        return false;
    }

}
