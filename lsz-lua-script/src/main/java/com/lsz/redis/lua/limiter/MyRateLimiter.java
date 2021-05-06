package com.lsz.redis.lua.limiter;


import cn.hutool.core.thread.ThreadUtil;
import com.google.common.util.concurrent.RateLimiter;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.api.RedissonClient;

@Slf4j
@NoArgsConstructor
//@AllArgsConstructor
@Data
public class MyRateLimiter {

    private String name;

    private RedissonClient client;


    @Test
    public void rateLimiterTest() {
        RateLimiter rateLimiter = RateLimiter.create(1);
        while (true) {
//            double acquire = rateLimiter.acquire();
//            log.info("acquire = {}", acquire);

            boolean b = rateLimiter.tryAcquire();
            log.info("b = {}", b);
            ThreadUtil.sleep(500);
        }

    }


}
