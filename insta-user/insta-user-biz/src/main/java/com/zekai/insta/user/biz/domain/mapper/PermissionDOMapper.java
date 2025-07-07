package com.zekai.insta.user.biz.domain.mapper;

import com.zekai.insta.user.biz.domain.dataobject.PermissionDO;
import com.zekai.insta.user.biz.domain.dataobject.RolePermissionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PermissionDO record);

    int insertSelective(PermissionDO record);

    PermissionDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PermissionDO record);

    int updateByPrimaryKey(PermissionDO record);

    List<RolePermissionDO> selectByRoleIds(@Param("roleIds") List<Long> roleIds);

    /**
     * 查询 APP 端所有被启用的权限
     *在对应的 xml 文件中，带上 type = 3 条件，3 表示按钮权限，因为普通用户目前来看，只有按钮权限需要控制，如笔记发布、评论发布等。只同步这块的数据到 Redis 缓存中：
     * @return
     */
    List<PermissionDO> selectAppEnabledList();
}