package com.wioyber.kele.core.annotation;

import com.wioyber.kele.core.enums.LockEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author cjg
 * @since 2023/1/9
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface RedisLock {

    LockEnum lockEnum() default LockEnum.DEFAULT;

    long expireTime() default 10L;

    long renewalTime() default 0L;


}
