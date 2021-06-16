package com.lsz.redis.doublewrite.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfiguration {

    @Value("${spring.redis.host}")
    String redisHost;

    @Value("${spring.redis.port}")
    Integer redisPort;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setDatabase(0)
                .setAddress("redis://" + redisHost + ":" + redisPort);
        return Redisson.create(config);
    }

}
