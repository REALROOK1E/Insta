package com.zekai.insta.oss.biz.strategy;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: ZeKai
 * @date: 2025/6/28
 * @description:
 **/
public interface FileStrategy {

    /**
     * 文件上传
     *
     * @param file
     * @param bucketName
     * @return
     */
    String uploadFile(MultipartFile file, String bucketName);

}
