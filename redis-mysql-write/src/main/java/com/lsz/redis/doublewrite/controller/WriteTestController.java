package com.lsz.redis.doublewrite.controller;

import cn.hutool.core.date.DateUtil;
import com.lsz.redis.doublewrite.entity.ValueEntity;
import com.lsz.redis.doublewrite.mapper.ValueDao;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Slf4j
public class WriteTestController {

    StringCodec codec = new StringCodec();

    @Autowired
    ValueDao valueDao;

    @Autowired
    RedissonClient redisson;

    @GetMapping("/demo")
    public String demo() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("demo ===> {}", sdf.format(date));
        return "demo ===> " + DateUtil.now();
    }

    @GetMapping("/value")
    public Long getValue() {
        Long id = 1L;
        // 从缓存拿
        RBucket<String> bucket = redisson.getBucket(id.toString(), codec);
        String value = bucket.get();
        if (value != null) {
            return Long.parseLong(value);
        }
        // 缓存未命中，从数据库拿，并保存到redis
        ValueEntity valueEntity = valueDao.selectValueById(id);
        Long dbValue = valueEntity.getValue();
        bucket.set(dbValue.toString());
        return dbValue;
    }

    @GetMapping("/doubleWrite")
    public Long doubleWrite() {
        log.info("===> doubleWrite");
        Long id = 1L;

        // 拿到value，更新数据库
        Long value = getValue();
        log.info("===> value = {}", value);
        long currentValue = value - 1;
        int affectedRows = valueDao.casUpdateValue(id, currentValue, value);
        // 使缓存失效
        RBucket<String> bucket = redisson.getBucket(id.toString(), codec);
        bucket.delete();
        log.info("===> delete cache");

        if (affectedRows == 1) {
            log.info("===> currentValue = {}", currentValue);
            return currentValue;
        } else {
            log.error("===> update failed");
            return -1L;
        }

    }


}
