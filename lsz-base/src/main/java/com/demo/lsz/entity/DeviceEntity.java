package com.demo.lsz.entity;

import com.demo.lsz.util.GenUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
public class DeviceEntity {

    private long id;
    private String name;
    private double longitude;
    private double latitude;

    public DeviceEntity() {
        this(GenUtil.getId());
    }

    public DeviceEntity(String name) {
        this(GenUtil.getId(), name);
    }

    public DeviceEntity(long id) {
        this(id, "device" + id, GenUtil.getLongitude(), GenUtil.getLatitude());
    }

    public DeviceEntity(long id, String name) {
        this(id, name, GenUtil.getLongitude(), GenUtil.getLatitude());
    }

}
