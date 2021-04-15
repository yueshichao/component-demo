package com.lsz.redis.ratelimiter;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

/*
* 参考：
* https://blog.csdn.net/yanluandai1985/article/details/104885089
* https://github.com/oneone1995/blog/issues/13
*
*
* */
@Slf4j
public class RateLimiterMain {

    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setDatabase(0)
                .setAddress("redis://192.168.0.106:6379");
        RedissonClient redisson = Redisson.create(config);

        // 测试
        StringCodec codec = new StringCodec();
        RBucket<String> testBucket = redisson.getBucket("test", codec);
        testBucket.set("test");

        // 初始化RateLimiter，1秒10个令牌
        RRateLimiter rateLimiter = redisson.getRateLimiter("testRateLimiter");
        rateLimiter.setRate(RateType.OVERALL, 10, 1, RateIntervalUnit.SECONDS);

        // 限流执行
        while (true) {
            rateLimiterRun(rateLimiter);
            ThreadUtil.sleep(1000);
        }

//        redisson.shutdown();


    }

    private static void rateLimiterRun(RRateLimiter rateLimiter) {
        if (rateLimiter.tryAcquire(1)) {
            log.info("O(∩_∩)O哈哈~");
        } else {
            log.error("没拿到令牌，鸽了~");
        }
    }

}
