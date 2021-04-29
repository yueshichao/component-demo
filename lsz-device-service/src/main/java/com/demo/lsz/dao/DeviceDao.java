package com.demo.lsz.dao;

import com.demo.lsz.entity.DeviceEntity;
import com.demo.lsz.util.GenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class DeviceDao {

    private static Map<Long, DeviceEntity> map = new ConcurrentHashMap<>();

    static {
        List<DeviceEntity> collect = Stream.of(
                new DeviceEntity("设备1"),
                new DeviceEntity("设备2"),
                new DeviceEntity(),
                new DeviceEntity(),
                new DeviceEntity(),
                new DeviceEntity(),
                new DeviceEntity("设备 final")
        ).collect(Collectors.toList());
        collect.stream().forEach(e -> map.put(e.getId(), e));
    }

    public DeviceEntity get(long id) {
        return map.get(id);
    }

    public DeviceEntity add(DeviceEntity entity) {
        long id = GenUtil.getId();
        entity.setId(id);

        map.put(id, entity);
        return entity;
    }


    public DeviceEntity remove(long id) {
        DeviceEntity entity = map.get(id);
        map.remove(id);
        return entity;
    }

    public List<DeviceEntity> findAll() {
        return new ArrayList<>(map.values());
    }
}
