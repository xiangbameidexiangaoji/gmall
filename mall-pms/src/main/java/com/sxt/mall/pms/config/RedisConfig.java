package com.sxt.mall.pms.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    //配置 redisTemplates 序列化器
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        //创建RedisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        //设置序列化器
        //设置Redis中的value的序列化器
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        //创建Redis中的Key的序列化器
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        //设置Redis中的String类型的Value序列化器
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //设置Redis中的Hash类型的value序列化器
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        //设置Redis中的String类型的Key序列化器
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //设置Redis中的String类型的Key序列化器
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        return redisTemplate;
    }
}
