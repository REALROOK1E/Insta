package com.zekai.insta.auth.alarm.Impl;

import com.zekai.insta.auth.alarm.AlarmInterface;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: ZeKai
 * @date: 2025/6/22
 * @description:
 **/
@Slf4j
public class SmsAlarm implements AlarmInterface {

    /**
     * 发送告警信息
     *
     * @param message
     * @return
     */
    @Override
    public boolean send(String message) {
        log.info("==> 【短信告警】：{}", message);

        return true;
    }
}
