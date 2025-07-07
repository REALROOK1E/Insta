package com.zekai.insta.user.biz.service.impl;

import com.google.common.base.Preconditions;
import com.zekai.framework.common.enums.DeletedEnum;
import com.zekai.framework.common.enums.StatusEnum;
import com.zekai.framework.common.response.Response;

import com.zekai.framework.common.util.JsonUtils;
import com.zekai.framework.common.util.ParamUtils;
import com.zekai.insta.context.holder.LoginUserContextHolder;
import com.zekai.insta.oss.api.FileFeignApi;
import com.zekai.insta.user.biz.constant.RedisKeyConst;
import com.zekai.insta.user.biz.constant.RoleConstants;
import com.zekai.insta.user.biz.domain.dataobject.RoleDO;
import com.zekai.insta.user.biz.domain.dataobject.UserDO;
import com.zekai.insta.user.biz.domain.dataobject.UserRoleDO;
import com.zekai.insta.user.biz.domain.mapper.RoleDOMapper;
import com.zekai.insta.user.biz.domain.mapper.UserDOMapper;
import com.zekai.insta.user.biz.domain.mapper.UserRoleDOMapper;
import com.zekai.insta.user.biz.enums.GenderEnum;
import com.zekai.insta.user.biz.enums.ResponseCodeEnum;
import com.zekai.insta.user.biz.model.vo.UpdateUserInfoVO;
import com.zekai.insta.user.biz.service.UserService;
import com.zekai.insta.user.dto.req.RegisterUserReqDTO;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * @author: ZeKai
 * @date: 2025/6/29
 * @description:
 **/
@Service
@Slf4j
public class UserServiceimpl implements UserService {

    @Resource
    private UserDOMapper userDOMapper;

    @Resource
    private FileFeignApi fileFeignApi;

    @Resource
    private UserRoleDOMapper userRoleDOMapper;

    @Resource
    private RoleDOMapper roleDOMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 更新用户信息
     *
     * @param updateUserInfoReqVO
     * @return
     */
    @Override
    public Response<?> updateUserInfo(UpdateUserInfoVO updateUserInfoReqVO) {
        UserDO userDO = new UserDO();
        // 设置当前需要更新的用户 ID
        userDO.setId(LoginUserContextHolder.getUserId());
        // 标识位：是否需要更新
        boolean needUpdate = false;

        // 头像
        MultipartFile avatarFile = updateUserInfoReqVO.getAvatar();

        if (Objects.nonNull(avatarFile)) {
            // todo: 调用对象存储服务上传文件
            fileFeignApi.test();
        }

        // 昵称
        String nickname = updateUserInfoReqVO.getNickname();
        if (StringUtils.isNotBlank(nickname)) {
            Preconditions.checkArgument(ParamUtils.checkNickname(nickname), ResponseCodeEnum.NICK_NAME_VALID_FAIL.getErrorMessage());
            userDO.setUsername(nickname);
            needUpdate = true;
        }

        // 小哈书号
        String InstaId = updateUserInfoReqVO.getInstaId();
        if (StringUtils.isNotBlank(InstaId)) {
            Preconditions.checkArgument(ParamUtils.checkInstaId(InstaId), ResponseCodeEnum.INSTA_ID_VALID_FAIL.getErrorMessage());
            userDO.setInstaId(InstaId);
            needUpdate = true;
        }

        // 性别
        Integer sex = updateUserInfoReqVO.getSex();
        if (Objects.nonNull(sex)) {
            Preconditions.checkArgument(GenderEnum.isValid(sex), ResponseCodeEnum.SEX_VALID_FAIL.getErrorMessage());
            userDO.setSex(sex);
            needUpdate = true;
        }

        // 生日
        //很危险
        LocalDate birthday = updateUserInfoReqVO.getBirthday();
        if (Objects.nonNull(birthday)) {
            userDO.setBirthday(birthday);
            needUpdate = true;
        }

        // 个人简介
        String introduction = updateUserInfoReqVO.getIntroduction();
        if (StringUtils.isNotBlank(introduction)) {
            Preconditions.checkArgument(ParamUtils.checkLength(introduction, 100), ResponseCodeEnum.INTRODUCTION_VALID_FAIL.getErrorMessage());
            userDO.setIntroduction(introduction);
            needUpdate = true;
        }

        // 背景图
        MultipartFile backgroundImgFile = updateUserInfoReqVO.getBackgroundImg();
        if (Objects.nonNull(backgroundImgFile)) {
            // todo: 调用对象存储服务上传文件
        }

        if (needUpdate) {
            // 更新用户信息
            userDO.setUpdateTime(LocalDateTime.now());
            userDOMapper.updateByPrimaryKeySelective(userDO);
        }
        return Response.success();
    }

    /**
     * 用户注册
     *
     * @param registerUserReqDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<Long> register(RegisterUserReqDTO registerUserReqDTO) {
        String phone = registerUserReqDTO.getPhone();

        // 先判断该手机号是否已被注册

        UserDO userDO1 = userDOMapper.selectByPhone(phone);

        log.info("==> 用户是否注册, phone: {}, userDO: {}", phone, JsonUtils.toJsonString(userDO1));

        // 若已注册，则直接返回用户 ID
        if (Objects.nonNull(userDO1)) {
            return Response.success(userDO1.getId());
        }

        // 否则注册新用户
        // 获取全局自增的小哈书 ID
        Long instaId = redisTemplate.opsForValue().increment(RedisKeyConst.INSTA_ID_GENERATOR_KEY);

        UserDO userDO = UserDO.builder()
                .phone(phone)
                .instaId(String.valueOf(instaId)) // 自动生成小红书号 ID
                .username("小红薯" + instaId) // 自动生成昵称, 如：小红薯10000
                .status(StatusEnum.ENABLE.getValue()) // 状态为启用
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .isDeleted(DeletedEnum.NO.getValue()) // 逻辑删除
                .build();

        // 添加入库
        userDOMapper.insert(userDO);

        // 获取刚刚添加入库的用户 ID
        Long userId = userDO.getId();

        // 给该用户分配一个默认角色
        UserRoleDO userRoleDO = UserRoleDO.builder()
                .userId(userId)
                .roleId(RoleConstants.COMMON_USER_ROLE_ID)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .isDeleted(DeletedEnum.NO.getValue())
                .build();
        userRoleDOMapper.insert(userRoleDO);

        RoleDO roleDO = roleDOMapper.selectByPrimaryKey(RoleConstants.COMMON_USER_ROLE_ID);

        // 将该用户的角色 ID 存入 Redis 中
        List<String> roles = new ArrayList<>(1);
        roles.add(roleDO.getRoleKey());

        String userRolesKey = RedisKeyConst.buildUserRoleKey(userId);
        redisTemplate.opsForValue().set(userRolesKey, JsonUtils.toJsonString(roles));

        return Response.success(userId);
    }
}