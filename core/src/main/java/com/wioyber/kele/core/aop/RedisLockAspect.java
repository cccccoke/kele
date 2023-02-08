package com.wioyber.kele.core.aop;

import com.wioyber.kele.core.annotation.RedisLock;
import com.wioyber.kele.core.util.RedisLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author cjg
 * @since 2023/1/9
 */
@Slf4j
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class RedisLockAspect {

    @Resource
    private RedisLockUtil redisLockUtil;

    @Pointcut("@annotation(com.wioyber.kele.core.annotation.RedisLock)")
    public void redisLockPointCut() {

    }

    @Around("redisLockPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        RedisLock annotation = method.getAnnotation(RedisLock.class);
        long timeMillis = System.currentTimeMillis();
        if (redisLockUtil.lock(annotation.lockEnum().getKey(), annotation.lockEnum().getValue() + timeMillis, annotation.expireTime())) {
            try {
                joinPoint.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            } finally {
                redisLockUtil.unlock(annotation.lockEnum().getKey(), annotation.lockEnum().getValue() + timeMillis);
            }
        } else {
            log.error("获取锁异常");
        }
        return null;
    }
}
