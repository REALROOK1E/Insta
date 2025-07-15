package com.zekai.insta.user.relation.biz.rpc;

import com.zekai.framework.common.response.Response;
import com.zekai.insta.user.api.UserFeignApi;
import com.zekai.insta.user.dto.req.FindUserByIdReqDTO;
import com.zekai.insta.user.dto.resp.FindUserByIdRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: ZeKai
 * @date: 2025/7/15
 * @description:
 **/
@Component
public class UserRpcService {

    @Resource
    private UserFeignApi userFeignApi;

    /**
     * 根据用户 ID 查询
     *
     * @param userId
     * @return
     */
    public FindUserByIdRspDTO findById(Long userId) {
        FindUserByIdReqDTO findUserByIdReqDTO = new FindUserByIdReqDTO();
        findUserByIdReqDTO.setId(userId);

        Response<FindUserByIdRspDTO> response = userFeignApi.findById(findUserByIdReqDTO);

        if (!response.isSuccess() || Objects.isNull(response.getData())) {
            return null;
        }
        return response.getData();
    }


}
