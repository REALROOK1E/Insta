package com.zekai.insta.count.bizs.domain.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author: ZeKai
 * @date: 2025/7/18
 * @description:
 **/
public interface UserCountDOMapper {
    int insertOrUpdateFollowingTotalByUserId(@Param("count") Integer count, @Param("userId") Long userId);
}
