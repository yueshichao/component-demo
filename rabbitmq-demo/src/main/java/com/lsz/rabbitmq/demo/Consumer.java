package com.lsz.rabbitmq.demo;

import com.lsz.rabbitmq.util.MqUtil;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.lsz.rabbitmq.util.MqUtil.QUEUE_NAME;

@Slf4j
public class Consumer {


    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = MqUtil.getChannel();

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

        log.info("消息者准备完毕...");

    }

}
