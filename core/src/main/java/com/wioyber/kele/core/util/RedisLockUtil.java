package com.wioyber.kele.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 使用redisTemplate 加锁 释放锁等 单机版
 * @author cjg
 * @since 2023/1/9
 */
@Slf4j
@Component
public class RedisLockUtil {


    private static final byte[] SCRIPT_RELEASE_LOCK = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end".getBytes();

    @Resource
    private RedisTemplate<String,Object> redisTemplate;


    //加锁
   public Boolean lock(String key, String value, long expireTime){
        log.info("加锁.....");
        Boolean execute = redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> redisConnection.set(key.getBytes(), value.getBytes(), Expiration.from(expireTime, TimeUnit.SECONDS), RedisStringCommands.SetOption.SET_IF_ABSENT));
        log.info("加锁.....{}", Boolean.TRUE.equals(execute) ? "成功":"失败");
        return execute;
    }
    //续期
    Boolean renewal(){
        return null;
    }

    //释放锁
    public Boolean unlock(String key, String value){
        log.info("释放锁.....");
        Boolean execute = redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> redisConnection.eval(SCRIPT_RELEASE_LOCK, ReturnType.BOOLEAN, 1, key.getBytes(), value.getBytes()));
        log.info("释放锁.....{}", Boolean.TRUE.equals(execute) ? "成功":"失败");
        return execute;
    }
}
