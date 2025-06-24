package com.zekai.insta.auth.Controller;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author: ZeKai
 * @date: 2025/6/24
 * @description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private String name;

    private LocalDateTime time;
}
