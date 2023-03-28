package com.wioyber.kele.core.redis;

import com.wioyber.kele.core.KeleApplicationTests;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author cjg
 * @since 2023/3/26
 */
class RedisDataOperationTest extends KeleApplicationTests {

    @Resource
    private RedisDataOperation redisDataOperation;
    @Test
    void stringTypeOperation() {
        redisDataOperation.stringTypeOperation();
    }
}