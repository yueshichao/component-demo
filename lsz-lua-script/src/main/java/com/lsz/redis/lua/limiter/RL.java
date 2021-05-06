package com.lsz.redis.lua.limiter;

public interface RL {

    boolean tryAcquire();

}
