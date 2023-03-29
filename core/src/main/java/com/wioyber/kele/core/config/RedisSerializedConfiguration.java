package com.wioyber.kele.core.config;

import com.wioyber.kele.core.support.RedisObjectSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author cjg
 * @since 2023/3/30
 */
@Configuration
public class RedisSerializedConfiguration {

    @Bean(name = "customRedisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        //调整序列化方式为json字符串
        template.setValueSerializer(new RedisObjectSerializer());

        template.afterPropertiesSet();
        return template;
    }
}
