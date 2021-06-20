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
public class Consumer2 {


    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = MqUtil.getChannel();

        // 快接收消息
        DeliverCallback deliverCallback = (tag, msg) -> {
            log.info("应答失败：{}", new String(msg.getBody()));
            ThreadUtil.sleep(30000);// 在这段时间内关闭进程，该消费者未消费的消息会给另一个消费者
            // 手动应答
//            channel.basicAck(msg.getEnvelope().getDeliveryTag(), false);
        };

        // 取消消息时的回调
        CancelCallback cancelCallback = (tag) -> {
            log.info(tag);
        };

        channel.basicConsume(QUEUE_NAME, false, deliverCallback, cancelCallback);

        log.info("消费者准备完毕...");

    }

}
