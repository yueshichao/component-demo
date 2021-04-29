package com.demo.lsz.service;

import com.demo.lsz.config.Const;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeviceChangeService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;


    @KafkaListener(topics = {Const.Topic.DEVICE_MOVE})
    public void onMessage(ConsumerRecord<?, ?> record) {
        log.info("topic = {}, partition = {}, value = {}", record.topic(), record.partition(), record.value());
    }



}
