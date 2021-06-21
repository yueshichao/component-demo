package com.lsz.rabbitmq.exchange.direct;

import com.lsz.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DirectConsumer2 {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 声明一个队列
        String queueName = "disk";
        channel.queueDeclare(queueName, false, false, false, null);

        channel.queueBind(queueName, EXCHANGE_NAME, "error");

        DeliverCallback deliverCallback = (consumerTag, msg) -> {
            log.info("DirectConsumer2消息：{}", new String(msg.getBody()));
        };

        channel.basicConsume(queueName, true, deliverCallback, (consumerTag) -> {});


    }


}
