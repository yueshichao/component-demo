package com.lsz.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.util.HashSet;
import java.util.Set;

/*
* https://github.com/redisson/redisson-examples
* */
@Slf4j
public class RedissonTest {


    @Test
    public void simpleUse() throws InterruptedException {
        log.info("test case start...");

        // 1. connect
        // 1.1 Create config object
        Config config = new Config();
        config.useSingleServer().setDatabase(1)
                // use "rediss://" for SSL connection
                .setAddress("redis://192.168.159.129:6379");

        // 1.2 Create Redisson instance
        // Sync and Async API
        RedissonClient redisson = Redisson.create(config);
        log.debug("redisson = {}", redisson);

        // 2. use
        // 2.1 k - v
        StringCodec codec = new StringCodec("utf-8");
        RBucket<Object> cityBucket = redisson.getBucket("city", codec);
        cityBucket.set("NanJing");

        // 2.2 Set
        RSet<Object> nickname = redisson.getSet("nickname", codec);
        nickname.add("jerry");
        nickname.add("tom");
//        nickname.union(nickname.getName()); // 做并集，并写入
//        Set<Object> readUnion = nickname.readUnion(nickname.getName()); // 做并集，不写入，返回
        // 还可以 diff - 差集， intersection - 交集

        // 2.3 ZSet
        RSortedSet<Object> film = redisson.getSortedSet("film", codec);
        RFuture<Boolean> filmFuture = film.addAsync("Kill Bill");
        filmFuture.await();
        film.add("Django Unchained");
        film.add("Kill Bill");

        // 2.4 Map
        RMap<Object, Object> songMap = redisson.getMap("song", codec);
        songMap.put("jay", "qhc");
        songMap.put("vae", "yhbk");

        // 2.5 List
        RList<Object> projectList = redisson.getList("project", codec);
        projectList.add("2");
        projectList.addBefore("2", "1");
        projectList.addAfter("2", "3");

        // 2.6 Score Sorted Set
        RScoredSortedSet<String> hotNewsSet = redisson.getScoredSortedSet("hot_news");
        hotNewsSet.add(100, "news0");
        hotNewsSet.add(-1, "news1");
        hotNewsSet.add(101, "news2");
        hotNewsSet.addScore("news0", 2);
        String first = hotNewsSet.first();
        log.info("first = {}", first);
        String last = hotNewsSet.last();
        log.info("last = {}", last);
        log.info("readAll = {}", hotNewsSet.readAll());

        redisson.shutdown();
        log.info("test case end...");
    }

}
