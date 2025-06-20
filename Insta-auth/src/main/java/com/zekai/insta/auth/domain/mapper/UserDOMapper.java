package com.zekai.insta.auth.domain.mapper;

/**
 * @author: ZeKai
 * @date: 2025/6/20
 * @description:
 **/
import com.zekai.insta.auth.domain.dataobject.UserDO;

public interface UserDOMapper {

    /**
     * searchbyid
     * @param id
     * @return
     */
    UserDO selectByPrimaryKey(Long id);

    /**
     * deleteby id
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert
     * @param record
     * @return
     */
    int insert(UserDO record);

    /**
     * update
     * @param record
     * @return
     */
    int updateByPrimaryKey(UserDO record);
}