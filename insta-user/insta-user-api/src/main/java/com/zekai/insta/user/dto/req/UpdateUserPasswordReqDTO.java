package com.zekai.insta.user.dto.req;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author: ZeKai
 * @date: 2025/7/7
 * @description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserPasswordReqDTO {

    @NotBlank(message = "密码不能为空")
    private String encodePassword;

}