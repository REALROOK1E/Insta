package com.zekai.config;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer;
import com.zekai.framework.common.constrants.Timeconsts;

import com.zekai.framework.common.util.JsonUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.TimeZone;

/**
 * @author: ZeKai
 * @date: 2025/6/24
 * @description:
 **/

    @AutoConfiguration
    public class JacksonAutoConfiguration {

        @Bean
        public ObjectMapper objectMapper() {
            // 初始化一个 ObjectMapper 对象，用于自定义 Jackson 的行为
            ObjectMapper objectMapper = new ObjectMapper();

            // 忽略未知属性
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            // 设置凡是为 null 的字段，返参中均不返回，请根据项目组约定是否开启
            // objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            // 设置时区
            objectMapper.setTimeZone(TimeZone.getTimeZone("Europe/Stockholm"));

            // JavaTimeModule 用于指定序列化和反序列化规则
            JavaTimeModule javaTimeModule = new JavaTimeModule();

            // 支持 LocalDateTime、LocalDate、LocalTime
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(Timeconsts.DateConstants.DATE_FORMAT_Y_M_D_H_M_S));
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(Timeconsts.DateConstants.DATE_FORMAT_Y_M_D_H_M_S));
            javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(Timeconsts.DateConstants.DATE_FORMAT_Y_M_D));
            javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(Timeconsts.DateConstants.DATE_FORMAT_Y_M_D));
            javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(Timeconsts.DateConstants.DATE_FORMAT_H_M_S));
            javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(Timeconsts.DateConstants.DATE_FORMAT_H_M_S));
            // 支持 YearMonth
            javaTimeModule.addSerializer(YearMonth.class, new YearMonthSerializer(Timeconsts.DateConstants.DATE_FORMAT_Y_M));
            javaTimeModule.addDeserializer(YearMonth.class, new YearMonthDeserializer(Timeconsts.DateConstants.DATE_FORMAT_Y_M));

            objectMapper.registerModule(javaTimeModule);

            // 初始化 JsonUtils 中的 ObjectMapper
            JsonUtils.init(objectMapper);

            return objectMapper;
        }

    }


