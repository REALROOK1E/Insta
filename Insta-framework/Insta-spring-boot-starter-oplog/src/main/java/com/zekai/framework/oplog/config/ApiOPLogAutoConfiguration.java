package com.zekai.framework.oplog.config;
import com.zekai.framework.oplog.aspect.ApiOperationLogAspect;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
/**
 * @author: ZeKai
 * @date: 2025/5/30
 * @description: 这是一个自动配置类，用于配置 API 操作日志记录功能，
 * 并且通过 @Bean 注解的 apiOperationLogAspect() 方法来创建一个 ApiOperationLogAspect 实例，
 * 以实现注入到 Spring 容器中。
 **/

@AutoConfiguration
public class ApiOPLogAutoConfiguration {

    @Bean
    public ApiOperationLogAspect apiOperationLogAspect() {
        return new ApiOperationLogAspect();
    }
}
