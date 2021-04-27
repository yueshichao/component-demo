package com.lsz.redis.lua;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.IOException;

@Slf4j
public class LuaMain {

    public static void main(String[] args) throws IOException {
        Config config = new Config();
        config.useSingleServer().setDatabase(0)
                .setAddress("redis://192.168.159.129:6379");
        RedissonClient redisson = Redisson.create(config);

        // test
        Object eval = redisson.getScript().eval(RScript.Mode.READ_WRITE, "return redis.call('set', 'name', 'lsz');", RScript.ReturnType.STATUS);
        log.info("result = {}", eval);

    }


}
