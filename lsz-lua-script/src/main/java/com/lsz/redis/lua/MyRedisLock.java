package com.lsz.redis.lua;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Slf4j
@RequiredArgsConstructor
public class MyRedisLock implements Lock {

    private final String name;

    private final RedissonClient client;

    private UUID uuid = UUID.randomUUID();

    private int expireTime = 1000 * 1000;


    @Override
    public void lock() {
        String lockId = uuid.toString() + ":" + Thread.currentThread().getId();
        log.info("lockId = {}", lockId);
        /*
        * 锁流程：
        * 先判断是否有锁，无则加锁
        * 有则判断是否重入，重用则value + 1
        * 不是重入则返回锁剩余时间
        *
        * */
        Object eval = client.getScript(new StringCodec()).eval(RScript.Mode.READ_WRITE,
                "if (redis.call('exists', KEYS[1]) == 0) then\n" +
                        "    redis.call('hincrby', KEYS[1], ARGV[2], 1);\n" +
                        "    redis.call('pexpire', KEYS[1], ARGV[1]);\n" +
                        "    return nil;\n" +
                        "end\n" +
                        "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then\n" +
                        "    redis.call('hincrby', KEYS[1], ARGV[2], 1);\n" +
                        "    redis.call('pexpire', KEYS[1], ARGV[1]);\n" +
                        "    return nil;\n" +
                        "end\n" +
                        "return redis.call('pttl', KEYS[1]);",
                RScript.ReturnType.STATUS,
                Collections.singletonList(name),
                expireTime + "", lockId
        );
        if (eval != null) {
            log.debug("lock, type = {}, result ===> {}", eval.getClass().getSimpleName(), eval.toString());
        } else {
            log.debug("lock, result ===> null");
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        String lockId = uuid.toString() + ":" + Thread.currentThread().getId();
        log.info("lockId = {}", lockId);
        /*
         * 解锁流程：
         * 先判断锁是不是自己的
         * 不是则直接不能解
         * 是则将计数减一，如果减到0则删除此key
         * TODO
         * */
        Object eval = client.getScript(new StringCodec()).eval(RScript.Mode.READ_WRITE,
                "if(redis.call('hexists', KEYS[1], ARGV[2]) == 0) then return nil; end\n" +
                        "local counter = redis.call('hincry', KEYS[1], ARGV[2], 1);\n" +
                        "if(counter > 0) then\n" +
                        "    redis.call('pexpire', KEYS[1], ARGV[1]);\n" +
                        "    return 0;\n" +
                        "else\n" +
                        "    redis.call('del', KEYS[1]);\n" +
                        "    return 1;\n" +
                        "end",
                RScript.ReturnType.STATUS,
                Collections.singletonList(name),
                expireTime + "", lockId
        );
        if (eval != null) {
            log.debug("lock, type = {}, result ===> {}", eval.getClass().getSimpleName(), eval.toString());
        } else {
            log.debug("lock, result ===> null");
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
