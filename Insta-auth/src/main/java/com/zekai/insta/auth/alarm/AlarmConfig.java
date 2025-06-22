package com.zekai.insta.auth.alarm;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import com.zekai.insta.auth.alarm.Impl.AlarmStarter;
import com.zekai.insta.auth.alarm.Impl.SmsAlarm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author: ZeKai
 * @date: 2025/6/22
 * @description:在 Nacos 配置中心的场景下，@RefreshScope 的主要功能包括：
 *
 * 动态刷新配置：当 Nacos 配置中心的配置发生变化时，应用中的配置会自动更新，避免了手动重启应用的繁琐过程。
 * 重新加载 Bean：标注了 @RefreshScope 的 Bean 会在配置变化后重新加载，确保 Bean 使用最新的配置。
 * 与 Spring Cloud 集成：@RefreshScope 与 Spring Cloud 的配置管理机制紧密集成，能够无缝地处理配置更新事件。
 **/
@RefreshScope
@Configuration
public class AlarmConfig {

    @Value("${alarm.type}")
    private String alarmType;

    @Bean
    public AlarmInterface alarmHelper() {
        // 根据配置文件中的告警类型，初始化选择不同的告警实现类
        if (StringUtils.equals("sms", alarmType)) {
            return new SmsAlarm();
        } else if (StringUtils.equals("mail", alarmType)) {
            return new AlarmStarter();
        } else {
            throw new IllegalArgumentException("错误的告警类型...");
        }
    }
}