package com.lsz.demo.kafka.consumer;


import com.alibaba.fastjson.JSON;
import com.lsz.demo.kafka.controller.ProducerController;
import com.lsz.demo.kafka.dto.MsgDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExactlyOnceConsumer {


    @Autowired
    RedissonClient redissonClient;

    @KafkaListener(id = "apple-consumer-0", topics = {ProducerController.topic}, idIsGroup = false)
    public void onMessage(ConsumerRecord<?, ?> record, Acknowledgment ack) {
        log.debug("topic = {}, partition = {}, value = {}", record.topic(), record.partition(), record.value());
        MsgDTO msgDTO = JSON.parseObject(record.value().toString(), MsgDTO.class);
        String msgId = msgDTO.getId();
        log.info("准备消费 id = {}", msgId);
        if (!consumed(msgId)) {
            log.info("处理消息中 id = {}", msgId);
        } else {
            log.warn("已处理过 id = {}", msgId);
        }
        ack.acknowledge();
    }


    private final static StringCodec codec = new StringCodec();
    private final static String name = "consumed_id";

    private boolean consumed(String msgId) {

        RSet<String> set = redissonClient.getSet(name, codec);
        if (set.contains(msgId)) {
            return true;
        }
        set.add(msgId);
        return false;
    }

}

