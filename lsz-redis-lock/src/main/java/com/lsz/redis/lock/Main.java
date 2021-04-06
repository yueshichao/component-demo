package com.lsz.redis.lock;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/*
* 1. redisson github地址
* https://github.com/redisson/redisson
*
* 2. 小米信息部技术团队 - 分布式锁的实现之 redis 篇
* https://xiaomi-info.github.io/2019/12/17/redis-distributed-lock/
*
*
* */
@Slf4j
public class Main {

    public static void main(String[] args) throws IOException {
        Config config = new Config();
        config.useSingleServer().setDatabase(0)
                .setAddress("redis://192.168.159.129:6379");
        RedissonClient redisson = Redisson.create(config);

        // redis分布式锁
        RLock bizLock = redisson.getLock("biz");
        bizLock.lock(100, TimeUnit.SECONDS);
        try {
            ThreadUtil.sleep(50000);
            log.info("do sth...");
        } finally {
            bizLock.unlock();
        }


        redisson.shutdown();

    }


}
