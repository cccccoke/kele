package com.wioyber.kele.core.config;

import com.wioyber.kele.core.support.RedisObjectSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author cjg
 * @since 2023/3/30
 */
@Configuration
public class RedisTemplateConfig {


    @Value("${spring.redis.cache.ttl}")
    private Long ttl;

    /**
     * 配置redis<Object,Object> 序列化
     *
     * @param redisConnectionFactory the redis connection factory
     * @return the redis template
     */
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

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(){
        return new LettuceConnectionFactory();
    }

    @Bean
    public CacheManager cacheManager() {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        RedisObjectSerializer redisObjectSerializer = new RedisObjectSerializer();
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(ttl))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisObjectSerializer))
                .disableCachingNullValues()
                .prefixCacheNameWith("kele:cache:data:");
        return RedisCacheManager
                .builder(lettuceConnectionFactory())
                .cacheDefaults(config)
                .build();
    }

}
