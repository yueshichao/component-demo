package com.lsz.rabbitmq.exchange.topic;

import com.lsz.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TopicConsumer2 {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();
        // 声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        // 声明队列
        String queueName = "Q2";
        channel.queueDeclare(queueName, false, false, false, null);
        String routingKey = "lazy.#";// # 表示多个单词
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);

        // 可以绑定多个routingKey
//        channel.queueBind(queueName, EXCHANGE_NAME, "a.b.rabbit");

        DeliverCallback deliverCallback = (consumerTag, msg) -> {
            log.info("TopicConsumer2， routingKey = {}, 接收消息 = {}",routingKey, new String(msg.getBody()));
        };
        channel.basicConsume(queueName, true, deliverCallback, tag -> {});
        
    }

}
