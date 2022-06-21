package com.lsz.info.push.config;

import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class RedisConfig {

    @Resource
    RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        redissonClient.getConfig().setCodec(new StringCodec());
    }


}
