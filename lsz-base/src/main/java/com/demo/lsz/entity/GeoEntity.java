package com.demo.lsz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
public class GeoEntity {

    // 经度
    private double longitude;
    // 纬度
    private double latitude;

    public static GeoEntity WU_XI = new GeoEntity(120.28429, 31.52853);
    public static GeoEntity SU_ZHOU = new GeoEntity(120.63132, 31.30227);

}
