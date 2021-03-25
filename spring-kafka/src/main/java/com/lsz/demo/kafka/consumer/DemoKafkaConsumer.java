package com.lsz.demo.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DemoKafkaConsumer {

    @KafkaListener(topics = {"demoTopic"})
    public void onMessage(ConsumerRecord<?, ?> record) {
        log.info("topic = {}, partition = {}, value = {}", record.topic(), record.partition(), record.value());
    }


}
