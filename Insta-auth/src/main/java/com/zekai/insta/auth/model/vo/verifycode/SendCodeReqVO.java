package com.zekai.insta.auth.model.vo.verifycode;


import com.zekai.framework.common.validator.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author: ZeKai
 * @date: 2025/6/20
 * @description: 入参实体类
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendCodeReqVO {
    @NotBlank(message = "手机号不能为空")
    @PhoneNumber
    private String phone;
}
