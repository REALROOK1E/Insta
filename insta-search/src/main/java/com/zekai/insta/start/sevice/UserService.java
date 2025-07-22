package com.zekai.insta.start.sevice;

import com.zekai.framework.common.response.PageResponse;
import com.zekai.insta.start.model.vo.SearchUserReqVO;
import com.zekai.insta.start.model.vo.SearchUserRspVO;

/**
 * ll
 *
 * @author Administrator
 * @version 1.0
 * @date 2025/7/22
 */
public interface UserService {

    /**
     * 搜索用户
     * @param searchUserReqVO
     * @return
     */
    PageResponse<SearchUserRspVO> searchUser(SearchUserReqVO searchUserReqVO);
}
