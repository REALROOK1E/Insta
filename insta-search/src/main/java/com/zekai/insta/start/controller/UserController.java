package com.zekai.insta.start.controller;
import com.zekai.framework.common.response.PageResponse;
import com.zekai.framework.oplog.aspect.ApiOperationLog;
import com.zekai.insta.start.model.vo.SearchUserReqVO;
import com.zekai.insta.start.model.vo.SearchUserRspVO;
import com.zekai.insta.start.sevice.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1
 *
 * @author Administrator
 * @version 1.0
 * @date 2025/7/22
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/user")
    @ApiOperationLog(description = "搜索用户")
    public PageResponse<SearchUserRspVO> searchUser(@RequestBody @Validated SearchUserReqVO searchUserReqVO) {
        return userService.searchUser(searchUserReqVO);
    }


}