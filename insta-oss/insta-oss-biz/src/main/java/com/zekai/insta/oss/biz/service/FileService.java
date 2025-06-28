package com.zekai.insta.oss.biz.service;
import com.zekai.framework.common.response.Response;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: ZeKai
 * @date: 2025/6/28
 * @description:
 **/
public interface FileService {

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    Response<?> uploadFile(MultipartFile file);
}