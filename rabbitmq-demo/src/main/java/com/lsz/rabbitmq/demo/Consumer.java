package com.lsz.rabbitmq.demo;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

@Slf4j
public class Consumer {

    private static final String QUEUE_NAME = "test_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 配置
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.0.109");
        factory.setUsername("admin");
        factory.setPassword("123");
        log.info("准备连接 ~ ");

        // 连接
        Connection connection = factory.newConnection(Executors.newFixedThreadPool(5));
        Channel channel = connection.createChannel();
        log.info("连接成功 ~ ");

        // 声明接收消息
        DeliverCallback deliverCallback = (tag, msg) -> {
            log.info(new String(msg.getBody()));
        };

        // 取消消息时的回调
        CancelCallback cancelCallback = (tag) -> {
            log.info(tag);
        };

        /*
        * 消费消息
        * 1. 消费哪个队列
        * 2. 消费成功后是否自动应答
        * 3. 消费者未成功消费的回调
        * 4. 消费者取消消费的回调
        *
        * */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);

        log.info("消息接收完毕...");

    }

}
