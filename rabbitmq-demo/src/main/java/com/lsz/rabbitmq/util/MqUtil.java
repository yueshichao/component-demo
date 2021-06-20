package com.lsz.rabbitmq.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

@Slf4j
public abstract class MqUtil {

    public static final String QUEUE_NAME = "test_queue";

    public static Channel getChannel() throws IOException, TimeoutException {
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
        return channel;
    }

}
