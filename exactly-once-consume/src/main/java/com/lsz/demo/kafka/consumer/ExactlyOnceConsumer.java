package com.lsz.demo.kafka.consumer;


import com.alibaba.fastjson.JSON;
import com.lsz.demo.kafka.controller.ProducerController;
import com.lsz.demo.kafka.dto.MsgDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
*
* 如何实现幂等消费，我的思路是：
* 1. 需要有全局唯一ID，消息带上这个
* 2. 利用redis，存储已消费的ID，如果消费过，就不再消费，如果未消费，就标记已消费
*
* 但现在有个问题，如果标记消费，但是消费失败，怎么办？
* 所以分为两个阶段进行，
* 第一步，消费前，查询消费中、消费完的数据，如果存在，不再重复消费，如果不存在，插入
* 第二步，消费后，删除消费中的数据，插入消费完的数据
*
*
*
* */
@Component
@Slf4j
public class ExactlyOnceConsumer {


    @Autowired
    RedissonClient redissonClient;

    public static final String ERROR_STR = "err";


    @KafkaListener(id = "apple-consumer-0", topics = {ProducerController.topic}, idIsGroup = false)
    public void onMessage(ConsumerRecord<?, ?> record, Acknowledgment ack) {
        log.debug("topic = {}, partition = {}, value = {}", record.topic(), record.partition(), record.value());
        MsgDTO msgDTO = JSON.parseObject(record.value().toString(), MsgDTO.class);

        String msgId = msgDTO.getId();
        log.info("准备消费 id = {}", msgId);
        if (preConsume(msgId)) {
            log.info("处理消息中 id = {}", msgId);
            handleMsg(msgDTO);
            markConsumed(msgId);
        } else {
            log.warn("已处理过 id = {}", msgId);
        }
        ack.acknowledge();
    }

    private void handleMsg(MsgDTO msgDTO) {
        String msg = msgDTO.getMsg();

        if (msg.contains(ERROR_STR)) {
            throw new RuntimeException(ERROR_STR);
        } else {
            log.info("处理消息完毕！");
        }
    }


    private final static StringCodec codec = new StringCodec();
    private final static String CONSUMED_KEY = "consumed_id";
    private final static String CONSUMING_KEY = "consuming_id";

    // 如果已消费或正在消费，不再重复消费，返回false
    // 如果未消费，返回true
    private boolean preConsume(String msgId) {

        Long eval = redissonClient.getScript(codec).eval(RScript.Mode.READ_WRITE,
                "if (redis.call('SISMEMBER', KEYS[1], ARGV[1]) == 0 and redis.call('SISMEMBER', KEYS[2], ARGV[1]) == 0) then\n" +
                        "    redis.call('SADD', KEYS[1], ARGV[1]);\n" +
                        "    return 1;\n" +
                        "end\n" +
                        "return 0;",
                RScript.ReturnType.STATUS,
                Stream.of(CONSUMING_KEY, CONSUMED_KEY).collect(Collectors.toList()),
                msgId);
        log.debug("class = {}, eval = {}", eval.getClass(), eval);
        return eval == 1;
    }


    // lua脚本保证原子性
    private void markConsumed(String msgId) {

        Object eval = redissonClient.getScript(codec).eval(RScript.Mode.READ_WRITE,
                "redis.call('SREM', KEYS[1], ARGV[1]);\n" +
                        "redis.call('SADD', KEYS[2], ARGV[1]);\n" +
                        "return nil;",
                RScript.ReturnType.STATUS,
                Stream.of(CONSUMING_KEY, CONSUMED_KEY).collect(Collectors.toList()),
                msgId);
        log.debug("eval = {}", eval);

    }


}

