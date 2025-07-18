package com.zekai.insta.context.filter;


import com.zekai.framework.common.constants.GlobalConstants;
import com.zekai.insta.context.holder.LoginUserContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.zekai.framework.common.constants.Timeconsts;

import java.io.IOException;
/**
 * @author: ZeKai
 * @date: 2025/6/26
 * @description: 提取请求头中的用户 ID 保存到上下文中，以方便后续使用
 **/
@Component

public class HeaderUserId2ContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        // 从请求头中获取用户 ID
        String userId = request.getHeader(GlobalConstants.USER_ID);



        // 判断请求头中是否存在用户 ID
        if (StringUtils.isBlank(userId)) {
            chain.doFilter(request, response);
            return;
        }
       // log.info("## HeaderUserId2ContextFilter, 用户 ID: {}", userId);

        LoginUserContextHolder.setUserId(userId);
        // 将请求和响应传递给过滤链中的下一个过滤器。
        try {
            chain.doFilter(request, response);
        }finally {
            LoginUserContextHolder.remove();
          //  log.info("===== 删除 ThreadLocal， userId: {}", userId);
        }


    }
}
