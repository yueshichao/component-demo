package com.lsz.rabbitmq.deadmsg.maxlen;

import com.lsz.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

// 死信队列消息来源：消息超过队列最大长度
@Slf4j
public class MaxLenProducer {

    public static final String NORMAL_EXCHANGE = "normal_ex";

    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();

        for (int i = 0; i < 11; i++) {
            String msg = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", null, msg.getBytes());
        }

    }

}
