package com.lsz.rabbitmq.manualack;

import cn.hutool.core.thread.ThreadUtil;
import com.lsz.rabbitmq.util.MqUtil;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.lsz.rabbitmq.util.MqUtil.QUEUE_NAME;

@Slf4j
public class Consumer1 {


    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = MqUtil.getChannel();

        // 快接收消息
        DeliverCallback deliverCallback = (tag, msg) -> {
            log.info("应答快：{}", new String(msg.getBody()));
            // 手动应答
            channel.basicAck(msg.getEnvelope().getDeliveryTag(), false);
        };

        // 取消消息时的回调
        CancelCallback cancelCallback = (tag) -> {
            log.info(tag);
        };

        // 0 - 公平分发， 1 - 不公平分发
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);


        channel.basicConsume(QUEUE_NAME, false, deliverCallback, cancelCallback);

        log.info("消费者准备完毕...");

    }

}
