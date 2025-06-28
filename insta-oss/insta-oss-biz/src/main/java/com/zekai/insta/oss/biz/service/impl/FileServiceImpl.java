package com.zekai.insta.oss.biz.service.impl;
import com.zekai.framework.common.response.Response;
import com.zekai.insta.oss.biz.service.FileService;
import com.zekai.insta.oss.biz.strategy.FileStrategy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
/**
 * @author: ZeKai
 * @date: 2025/6/28
 * @description:
 **/

    @Service
    @Slf4j
    public class FileServiceImpl implements FileService {

    @Resource
    private FileStrategy fileStrategy;

    private static final String BUCKET_NAME = "insta";

    @Override
    public Response<?> uploadFile(MultipartFile file) {
        // 上传文件
        String url = fileStrategy.uploadFile(file, BUCKET_NAME);

        return Response.success(url);
    }
    }


