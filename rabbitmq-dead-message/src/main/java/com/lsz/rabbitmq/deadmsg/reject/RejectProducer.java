package com.lsz.rabbitmq.deadmsg.reject;

import com.lsz.rabbitmq.util.MqUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

// 死信队列消息的来源：拒绝消息
@Slf4j
public class RejectProducer {

    public static final String NORMAL_EXCHANGE = "normal_ex";

    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();

        for (int i = 0; i < 11; i++) {
            String msg = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", null, msg.getBytes());
        }

    }

}
