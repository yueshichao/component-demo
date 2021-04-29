package com.demo.lsz.util;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSON;
import com.demo.lsz.entity.GeoEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static com.demo.lsz.entity.GeoEntity.SU_ZHOU;
import static com.demo.lsz.entity.GeoEntity.WU_XI;

@Slf4j
public abstract class GenUtil {

    static Snowflake snowflake = cn.hutool.core.util.IdUtil.createSnowflake(0L, 0L);
    static AtomicInteger atomicInteger = new AtomicInteger(0);
    static Random random = new Random();

    // 前端接不了太长的long，要么转成String传，否则用int
    public static long getId() {
        return atomicInteger.getAndIncrement();
//        return snowflake.nextId();
    }

    public static double getLongitude() {
        return randomLonLat(WU_XI.getLongitude(), SU_ZHOU.getLongitude(),
                WU_XI.getLatitude(), SU_ZHOU.getLatitude(),
                GeoType.LONGITUDE);
    }


    public static double getLatitude() {
        return randomLonLat(WU_XI.getLongitude(), SU_ZHOU.getLongitude(),
                WU_XI.getLatitude(), SU_ZHOU.getLatitude(),
                GeoType.LATITUDE);
    }

    enum GeoType {
        LONGITUDE,
        LATITUDE,
        ;
    }

    //    https://blog.csdn.net/fuyifang/article/details/52942717
    public static double randomLonLat(double MaxLon, double MinLon, double MaxLat, double MinLat, GeoType type) {
        BigDecimal db = new BigDecimal(Math.random() * (MaxLon - MinLon) + MinLon);
        double lon = db.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();// 小数后6位
        db = new BigDecimal(Math.random() * (MaxLat - MinLat) + MinLat);
        double lat = db.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (type == GeoType.LONGITUDE) {
            return lon;
        } else {
            return lat;
        }
    }



}
