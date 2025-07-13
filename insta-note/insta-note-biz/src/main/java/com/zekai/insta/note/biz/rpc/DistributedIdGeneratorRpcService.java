package com.zekai.insta.note.biz.rpc;
import com.zekai.insta.distributed.id.generator.api.DistributedIdGeneratorFeignApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author: ZeKai
 * @date: 2025/7/12
 * @description:
 **/
@Component
public class DistributedIdGeneratorRpcService {

    @Resource
    private DistributedIdGeneratorFeignApi distributedIdGeneratorFeignApi;

    /**
     * 生成雪花算法 ID
     *
     * @return
     */
    public String getSegmentId() {
        return distributedIdGeneratorFeignApi.getSegmentId("leaf-segment-test");
    }

}
