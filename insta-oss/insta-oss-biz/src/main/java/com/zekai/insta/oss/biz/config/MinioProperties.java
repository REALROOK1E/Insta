package com.zekai.insta.oss.biz.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: ZeKai
 * @date: 2025/6/28
 * @description:
 **/
@ConfigurationProperties(prefix = "minio")
@Component
@Data
public class MinioProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    //todo : aliyun 的服务先放一下，不买了
}