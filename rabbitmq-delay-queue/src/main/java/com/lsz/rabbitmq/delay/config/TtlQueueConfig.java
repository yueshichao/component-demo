package com.lsz.rabbitmq.delay.config;

import cn.hutool.core.map.MapUtil;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class TtlQueueConfig {

    // 普通交换机
    public static final String X_EXCHANGE = "X";
    // 死信交换机
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";

    // 普通队列
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    // 死信队列
    public static final String DEAD_LETTER_QUEUE = "QD";


    // 声明交换机
    @Bean("xExchange")
    public DirectExchange xExchange() {
        return new DirectExchange(X_EXCHANGE);
    }

    @Bean("yExchange")
    public DirectExchange yExchange() {
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    // 声明队列
    @Bean("queueA")
    public Queue queueA() {
        Map<String, Object> args = MapUtil.<String, Object>builder()
                .put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE) // 设置死信交换机
                .put("x-dead-letter-routing-key", "YD") // 死信routingKey
                .put("x-message-ttl", 10000) // 过期时间
                .build();
        return QueueBuilder.durable(QUEUE_A).withArguments(args).build();
    }

    @Bean("queueB")
    public Queue queueB() {
        Map<String, Object> args = MapUtil.<String, Object>builder()
                .put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE) // 设置死信交换机
                .put("x-dead-letter-routing-key", "YD") // 死信routingKey
                .put("x-message-ttl", 40000) // 过期时间
                .build();
        return QueueBuilder.durable(QUEUE_B).withArguments(args).build();
    }

    @Bean("queueD")// 死信队列
    public Queue queueD() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    // 绑定
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA,
                                  @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    @Bean
    public Binding queueBBindingX(@Qualifier("queueB") Queue queueB,
                                  @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }

    @Bean
    public Binding queueDBindingY(@Qualifier("queueD") Queue queueD,
                                  @Qualifier("yExchange") DirectExchange yExchange) {
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }

    // ****************************不设置队列过期时间****************************
    public static final String QUEUE_C = "QC";

    @Bean
    public Queue queueC() {
        Map<String, Object> args = MapUtil.<String, Object>builder()
                .put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE) // 设置死信交换机
                .put("x-dead-letter-routing-key", "YD") // 死信routingKey
                .build();
        return QueueBuilder.durable(QUEUE_C).withArguments(args).build();
    }

    @Bean
    public Binding queueCBindingX(@Qualifier("queueC") Queue queueC, @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }


}
