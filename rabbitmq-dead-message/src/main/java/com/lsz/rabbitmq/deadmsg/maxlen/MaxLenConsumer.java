package com.lsz.rabbitmq.deadmsg.maxlen;

import cn.hutool.core.map.MapUtil;
import com.lsz.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class MaxLenConsumer {

    public static final String NORMAL_EXCHANGE = "normal_ex";
    public static final String DEAD_EXCHANGE = "dead_ex";
    public static final String NORMAL_QUEUE = "normal_queue";
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();

        // ******************交换机******************
        // 声明死信和普通交换机的类型为direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, "direct");
        channel.exchangeDeclare(DEAD_EXCHANGE, "direct");

        // ******************队列******************
        // 声明死信队列
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);

        // 声明普通队列
        Map<String, Object> arguments = MapUtil.<String, Object>builder()
                .put("x-dead-letter-exchange", DEAD_EXCHANGE) // 死信交换机
                .put("x-dead-letter-routing-key", "lisi") // 死信routingKey
                .put("x-max-length", 6) // 队列长度最大为6
                .build();
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);

        // ******************bind******************
        // 绑定普通交换机与普通队列
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "zhangsan");

        // 绑定死信交换机与死信队列
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");

        // ******************消费******************
        log.info("等待接收消息...");
        DeliverCallback deliverCallback = (consumeTag, msg) -> {
            log.info("consumeTag = {}, msg = {}", consumeTag, new String(msg.getBody()));
        };

        channel.basicConsume(NORMAL_QUEUE, true, deliverCallback, consumerTag -> {});


    }

}
