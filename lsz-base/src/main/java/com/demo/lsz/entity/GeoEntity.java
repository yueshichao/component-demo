package com.demo.lsz.entity;

import cn.hutool.core.util.RandomUtil;
import com.demo.lsz.util.GenUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Data
@Slf4j
@AllArgsConstructor
public class GeoEntity {

    public static final Random random = new Random();

    // 经度
    protected double longitude;
    // 纬度
    protected double latitude;

    protected boolean ableAutoRandomMove = true;

    public void randomMove() {
        double horizontal = RandomUtil.randomDouble(-0.5, 0.5);
        double vertical = RandomUtil.randomDouble(-0.5, 0.5);

        longitude += horizontal;
        latitude += vertical;

    }

    public static void main(String[] args) {
        while (true) {
            double horizontal = RandomUtil.randomDouble(-1, 1);
            System.out.println("horizontal = " + horizontal);
        }
    }

    public GeoEntity() {
        this.longitude = GenUtil.getLongitude();
        this.latitude = GenUtil.getLatitude();
    }

    public GeoEntity(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static GeoEntity WU_XI = new GeoEntity(120.28429, 31.52853);
    public static GeoEntity SU_ZHOU = new GeoEntity(120.63132, 31.30227);

}
