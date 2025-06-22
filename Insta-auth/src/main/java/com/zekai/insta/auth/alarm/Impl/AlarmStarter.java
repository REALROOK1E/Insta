package com.zekai.insta.auth.alarm.Impl;

import com.zekai.insta.auth.alarm.AlarmInterface;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: ZeKai
 * @date: 2025/6/22
 * @description:
 **/
@Slf4j
public class AlarmStarter implements AlarmInterface {
    @Override
    public void send(String message) {
        log.info("==> 【邮件告警】：{}", message);
    }
}
