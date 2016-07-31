package com.ming.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登录验证方法注解
 * @author Ming
 * @description 进行Struts2登录验证方法的注解
 * @date 2015-03-15
 */
@Target (ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
public @interface Permission {
}
