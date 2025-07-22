package com.zekai.insta.start.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author: ZeKai
 * @date: 2025/6/21
 * @description:
 **/
@Getter
@AllArgsConstructor
public enum LoginType {
    VERIFICATION_CODE(1),

    PASSWORD(2);

    private final Integer value;

    public static LoginType valueOf(Integer code) {
        for (LoginType loginTypeEnum : LoginType.values()) {
            if (Objects.equals(code, loginTypeEnum.getValue())) {
                return loginTypeEnum;
            }
        }
        return null;
    }
}
