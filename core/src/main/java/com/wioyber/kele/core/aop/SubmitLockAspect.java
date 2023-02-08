package com.wioyber.kele.core.aop;

import com.wioyber.kele.core.annotation.SubmitLock;
import com.wioyber.kele.core.exception.BaseException;
import com.wioyber.kele.core.util.RedisLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author cjg
 * @since 2023/1/17
 */
@Slf4j
@Aspect
@Component
public class SubmitLockAspect {

    @Resource
    private RedisLockUtil redisLockUtil;

    @Pointcut("@annotation(com.wioyber.kele.core.annotation.SubmitLock)")
    public void submitLockAspect() {

    }

    @Before("submitLockAspect()")
    public void submitBefore(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(SubmitLock.class)) {
            SubmitLock annotation = method.getAnnotation(SubmitLock.class);
            long ttl = annotation.ttl();
            //加锁
            Boolean submitKey = redisLockUtil.lock("SUBMIT_KEY", methodSignature.getClass().toString(), ttl);
            if (!submitKey) {
                throw new BaseException(-1, "操作太频繁...");
            }
        }
    }

}
