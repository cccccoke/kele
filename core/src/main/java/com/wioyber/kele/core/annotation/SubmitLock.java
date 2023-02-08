package com.wioyber.kele.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author cjg
 * @since 2023/1/17
 * 防止重复提交
 */
@Documented
@Retention(RUNTIME)
@Target({ElementType.METHOD})
public @interface SubmitLock {
    long ttl() default 10L;

}
