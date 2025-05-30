package com.zekai.framework.oplog.aspect;

/**
 * @author: ZeKai
 * @date: 2025/5/30
 * @description:
 **/
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented

public @interface ApiOperationLog {

    String description() default "";

}
