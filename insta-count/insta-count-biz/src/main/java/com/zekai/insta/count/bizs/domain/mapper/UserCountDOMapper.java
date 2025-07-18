package com.zekai.insta.count.bizs.domain.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author: ZeKai
 * @date: 2025/7/18
 * @description:
 **/
public interface UserCountDOMapper {
    int insertOrUpdateFollowingTotalByUserId(@Param("count") Integer count, @Param("userId") Long userId);

    /**
     * 添加记录或更新笔记发布数
     * @param count
     * @param userId
     * @return
     */
    int insertOrUpdateNoteTotalByUserId(@Param("count") Long count, @Param("userId") Long userId);
}
