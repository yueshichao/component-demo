package com.lsz.rabbitmq.durable;

import com.lsz.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static com.lsz.rabbitmq.util.MqUtil.QUEUE_NAME;

@Slf4j
public class Producer {


    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = MqUtil.getChannel();

        // durable 队列持久化
        channel.queueDeclare(QUEUE_NAME, true, false, true, null);


        String msg = "hello!";
        // MessageProperties.PERSISTENT_TEXT_PLAIN 消息持久化
        channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        log.info("消息发送完毕...");


    }

}
