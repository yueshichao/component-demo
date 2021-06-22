package com.lsz.rabbitmq.deadmsg.ttl;

import com.lsz.rabbitmq.util.MqUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

// 死信队列消息的来源：过期消息
@Slf4j
public class Producer {

    public static final String NORMAL_EXCHANGE = "normal_ex";

    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();

        // 死信消息，设置TTL时间，单位是ms
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();

        for (int i = 0; i < 11; i++) {
            String msg = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", properties, msg.getBytes());
        }

    }

}
