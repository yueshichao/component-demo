package com.lsz.rabbitmq.delay.config;

import cn.hutool.core.map.MapUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/*
 * 可以使用rabbitmq的延迟队列插件，rabbitmq_delayed_message_exchange
 * 下载地址：https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/releases/download/v3.8.0/rabbitmq_delayed_message_exchange-3.8.0.ez
 * 解压放置在plugin目录下
 * 再使用命令rabbitmq-plugins enable rabbitmq_delayed_message_exchange
 *
 * */
@Configuration
public class DelayQueueConfig {


    public static final String DELAY_QUEUE_NAME = "delayed.queue";
    public static final String DELAY_EXCHANGE_NAME = "delayed.exchange";
    public static final String DELAY_ROUTING_KEY = "delayed.routingKey";

    // 交换机
    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = MapUtil.<String, Object>builder()
                .put("x-delayed-type", "direct")
                .build();
        return new CustomExchange(DELAY_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    // 队列
    @Bean
    public Queue delayQueue() {
        return new Queue(DELAY_QUEUE_NAME);
    }

    // routingKey
    @Bean
    public Binding delayQueueBindingDelayedExchange(@Qualifier("delayQueue") Queue delayQueue
            , @Qualifier("delayExchange") CustomExchange delayExchange) {
        return BindingBuilder.bind(delayQueue).to(delayExchange).with(DELAY_ROUTING_KEY).noargs();
    }


}
