package com.wioyber.kele.core.util;

import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author cjg
 * @since 2023/1/11
 */
@Component
public class RedissonLockUtil {

    @Resource
    private RedissonClient redissonClient;


}
