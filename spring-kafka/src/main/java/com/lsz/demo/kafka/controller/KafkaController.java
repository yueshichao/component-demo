package com.lsz.demo.kafka.controller;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class KafkaController {

    @Resource
    KafkaTemplate<String, String> kafkaTemplate;

    @Resource
    ProducerFactory<String, String> producerFactory;

    @GetMapping("/template/send")
    public String templateSend(@RequestParam String msg) {
        ListenableFuture<SendResult<String, String>> sendFuture = kafkaTemplate.send("demoTopic", msg);
        sendFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("producerRecord = {}", result.getProducerRecord());
                log.info("recordMetadata = {}", result.getRecordMetadata());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("{}", ex);
            }
        });
        return DateUtil.now() + " ===> " + msg;
    }

    @GetMapping("/producer/send")
    public String producerSend(@RequestParam String msg) {
        ProducerRecord<String, String> record = new ProducerRecord<>("demoTopic", msg);
        Producer<String, String> producer = producerFactory.createProducer();
        producer.send(record, (metadata, ex) -> {
            log.info("metadata = {}, ex = {}", metadata, ex);
            if (ex == null) {
                log.error("成功");
            } else {
                // 对消息重发或持久化
                log.error("失败");
            }
        });
        return DateUtil.now() + " ===> " + msg;
    }

}
