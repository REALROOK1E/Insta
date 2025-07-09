package com.zekai.insta.user.biz.rpc;
import com.zekai.insta.distributed.id.generator.api.DistributedIdGeneratorFeignApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
/**
 * @author: ZeKai
 * @date: 2025/7/8
 * @description:
 **/
@Component
public class DistributedIdGeneratorRpcService {

    @Resource
    private DistributedIdGeneratorFeignApi distributedIdGeneratorFeignApi;

    /**
     * Leaf 号段模式：小哈书 ID 业务标识
     */
    private static final String BIZ_TAG_XIAOHASHU_ID = "leaf-segment-xiaohashu-id";

    /**
     * 调用分布式 ID 生成服务生成小哈书 ID
     *
     * @return
     */
    public String getXiaohashuId() {
        return distributedIdGeneratorFeignApi.getSegmentId(BIZ_TAG_XIAOHASHU_ID);
    }
}