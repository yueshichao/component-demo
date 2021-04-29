package com.demo.lsz.service;

import com.alibaba.fastjson.JSON;
import com.demo.lsz.config.Const;
import com.demo.lsz.dao.DeviceDao;
import com.demo.lsz.entity.DeviceEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DeviceGeoPushService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    DeviceDao deviceDao;

    @Scheduled(cron = "0/10 * * * * ?")
    public void deviceAutoMove() {
        log.info("======= device move start =======");
        List<DeviceEntity> all = deviceDao.findAll();

        all.forEach(d -> {
                    if (d.isAbleAutoRandomMove()) {
                        deviceMoveRandom(d.getId());
                    }
                }
        );
        log.info("======= device move end =======");
    }

    public DeviceEntity deviceMoveRandom(long deviceId) {
        DeviceEntity entity = deviceDao.get(deviceId);
        entity.randomMove();
        String json = JSON.toJSONString(entity);
        log.info("deviceId = {}, ===> move = {}", deviceId, json);
        kafkaTemplate.send(Const.Topic.DEVICE_MOVE, json);
        return entity;
    }


}
