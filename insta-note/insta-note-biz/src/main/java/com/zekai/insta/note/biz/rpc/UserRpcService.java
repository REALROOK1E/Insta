package com.zekai.insta.note.biz.rpc;
import com.zekai.framework.common.response.Response;
import com.zekai.insta.user.api.UserFeignApi;
import com.zekai.insta.user.dto.req.FindUserByIdReqDTO;
import com.zekai.insta.user.dto.resp.FindUserByIdRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;
/**
 * @author: ZeKai
 * @date: 2025/7/13
 * @description: 将 Feign 查询用户信息接口封装成一个方法，代码如下：
 **/
@Component
public class UserRpcService {

    @Resource
    private UserFeignApi userFeignApi;

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    public FindUserByIdRspDTO findById(Long userId) {
        FindUserByIdReqDTO findUserByIdReqDTO = new FindUserByIdReqDTO();
        findUserByIdReqDTO.setId(userId);

        Response<FindUserByIdRspDTO> response = userFeignApi.findById(findUserByIdReqDTO);

        if (Objects.isNull(response) || !response.isSuccess()) {
            return null;
        }

        return response.getData();
    }

}
