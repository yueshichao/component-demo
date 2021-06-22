package com.lsz.rabbitmq.deadmsg.maxlen;

import com.lsz.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

// 仅接收死信队列
@Slf4j
public class MaxLenDeadMsgConsumer {

    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();

        log.info("等待接收消息...");

        DeliverCallback deliverCallback = (consumeTag, msg) -> {
            log.info("MaxLenDeadMsgConsumer, consumeTag = {}, msg = {}", consumeTag, new String(msg.getBody()));
        };

        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, consumerTag -> {});


    }

}
