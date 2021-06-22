package com.lsz.rabbitmq.deadmsg.ttl;

import com.lsz.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

// 仅接收死信队列
@Slf4j
public class Consumer2 {

    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();

        log.info("等待接收消息...");

        DeliverCallback deliverCallback = (consumeTag, msg) -> {
            log.info("Consumer2, consumeTag = {}, msg = {}", consumeTag, new String(msg.getBody()));
        };

        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, consumerTag -> {});


    }

}
