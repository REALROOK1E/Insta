package com.zekai.insta.user.biz.domain.dataobject;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class UserRoleDO {
    private Long id;

    private Long userId;

    private Long roleId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDeleted;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }


    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}