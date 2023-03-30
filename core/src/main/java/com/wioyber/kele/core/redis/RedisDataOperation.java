package com.wioyber.kele.core.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author cjg
 * @since 2023/3/26
 */
@Slf4j
@Component
public class RedisDataOperation {

    @Resource(name = "customRedisTemplate")
    private RedisTemplate<Object,Object> redisTemplate;

    /**
     * String type operation.
     */
    public void stringTypeOperation() {
        Object o = redisTemplate.opsForValue().get("kele:shiro:info:tk:09439a1e-8bd7-4e4e-ab0e-51df2de9b7bb");
        System.out.println(o);
//        redisTemplate.setKeySerializer(RedisSerializer.string());
//        log.info("string类型写入--------->{}", "string-type-operation--key");
//        redisTemplate.opsForValue().set("string-type-operation--key", "string-type-operation--value");
//        log.info("string类型读取--------->{}", redisTemplate.opsForValue().get("string-type-operation--key"));
//        redisTemplate.opsForValue().set("docker-redis-key", "docker-redis-value");
//        log.info("docker-redis-key----->{}", redisTemplate.opsForValue().get("docker-redis-key"));
    }

}
