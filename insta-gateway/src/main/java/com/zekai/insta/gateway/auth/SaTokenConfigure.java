package com.zekai.insta.gateway.auth;


import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: ZeKai
 * @date: 2025/6/22
 * @description: 主要就是在这鉴权逻辑
 **/
@Configuration
@Slf4j
public class SaTokenConfigure {
    // 注册 Sa-Token全局过滤器
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截地址
                .addInclude("/**")    /* 拦截全部path */
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    // 登录校验
                    log.info("鉴权");
                    SaRouter.match("/**") // 拦截所有路由
                            .notMatch("/auth/user/login") // 排除登录接口
                            .notMatch("/auth/verification/code/send")
                            .notMatch("/auth/user/password/update") // 排除验证码发送接口
                           // 校验是否登录
                    ;
                    //log.info("角色{}", StpUtil.getRoleList().toString());
                    // 权限认证 -- 不同模块, 校验不同权限
                    //SaRouter.match("/auth/user/logout", r -> StpUtil.checkPermission("app:note:publish"));
                    //log.info("权限{}",StpUtil.getPermissionList().toString());
                     //SaRouter.match("/auth/user/logout", r -> StpUtil.checkRole("admin"));
                    // SaRouter.match("/goods/**", r -> StpUtil.checkPermission("goods"));
                    // SaRouter.match("/orders/**", r -> StpUtil.checkPermission("orders"));

                    // 更多匹配 ...  */
                }).setError(e -> {
                    if (e instanceof NotLoginException) { // 未登录异常
                        return SaResult.error("未登录：" + e.getMessage());
                    } else if (e instanceof NotPermissionException || e instanceof NotRoleException) { // 权限不足，或不具备角色，统一抛出权限不足异常
                        return SaResult.error("未登录：" + e.getMessage());
                    } else { // 其他异常，则抛出一个运行时异常
                        log.info("404了");
                        return SaResult.error("未登录：" + e.getMessage());
                    }
                }  );
    }     // 异常处理方法：每次setAuth函数出现异常时进入;
    }

