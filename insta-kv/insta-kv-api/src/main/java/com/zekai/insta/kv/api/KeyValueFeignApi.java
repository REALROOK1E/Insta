package com.zekai.insta.kv.api;

import com.zekai.framework.common.response.Response;
import com.zekai.insta.kv.constant.ApiConstants;
import com.zekai.insta.kv.dto.req.AddNoteContentReqDTO;
import com.zekai.insta.kv.dto.req.DeleteNoteContentReqDTO;
import com.zekai.insta.kv.dto.req.FindNoteContentReqDTO;
import com.zekai.insta.kv.dto.rsp.FindNoteContentRspDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: ZeKai
 * @date: 2025/7/8
 * @description:
 **/
@FeignClient(name = ApiConstants.SERVICE_NAME)
public interface KeyValueFeignApi {

    String PREFIX = "/kv";

    @PostMapping(value = PREFIX + "/note/content/add")
    Response<?> addNoteContent(@RequestBody AddNoteContentReqDTO addNoteContentReqDTO);

    @PostMapping(value = PREFIX + "/note/content/find")
    Response<FindNoteContentRspDTO> findNoteContent(@RequestBody FindNoteContentReqDTO findNoteContentReqDTO);

    @PostMapping(value = PREFIX + "/note/content/delete")
    Response<?> deleteNoteContent(@RequestBody DeleteNoteContentReqDTO deleteNoteContentReqDTO);
}
