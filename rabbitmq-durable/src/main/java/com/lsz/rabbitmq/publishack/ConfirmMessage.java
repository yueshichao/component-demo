package com.lsz.rabbitmq.publishack;

import com.lsz.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.lsz.rabbitmq.util.MqUtil.QUEUE_NAME;

/*
*
* 1. 单个确认
* 2. 批量确认
* 3. 异步确认
*
* */
@Slf4j
public class ConfirmMessage {

    public static final int MSG_COUNT = 1000;

    public static void main(String[] args) throws Exception {

        long l0 = System.currentTimeMillis();
        publishIndividually();// 单个确认
        long l1 = System.currentTimeMillis();
        log.info("单个确认 ---> {} ms", l1 - l0);

        long l2 = System.currentTimeMillis();
        publishPatch();
        long l3 = System.currentTimeMillis();
        log.info("批量确认 ---> {} ms", l3 - l2);


        long l4 = System.currentTimeMillis();
        publishAsync();
        long l5 = System.currentTimeMillis();
        log.info("异步确认 ---> {} ms", l5 - l4);


    }

    private static void publishAsync() throws IOException, TimeoutException, InterruptedException {
        Channel channel = MqUtil.getChannel();

        String queueName = "publishPatch";
        // durable 队列持久化
        channel.queueDeclare(queueName, true, false, true, null);


        // 消息确认成功回调，multiple表示是否为批量确认
        ConfirmCallback ackCallback = (deliveryTag, multiple) -> {
            log.info("消息发送成功, deliveryTag = {}, multiple = {}", deliveryTag, multiple);
        };

        // 消息失败回调
        ConfirmCallback nackCallback = (deliveryTag, multiple) -> {
            log.warn("消息发送失败, deliveryTag = {}, multiple = {}", deliveryTag, multiple);
        };

        channel.addConfirmListener(ackCallback, nackCallback);

        // 开启发布确认
        channel.confirmSelect();

        for (int i = 0; i < MSG_COUNT; i++) {
            String msg = "" + i;
            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        }

    }

    private static void publishPatch() throws IOException, TimeoutException, InterruptedException {
        Channel channel = MqUtil.getChannel();

        String queueName = "publishPatch";
        // durable 队列持久化
        channel.queueDeclare(queueName, true, false, true, null);

        // 开启发布确认
        channel.confirmSelect();

        int batchSize = 100;

        for (int i = 0; i < MSG_COUNT; i++) {
            String msg = "" + i;
            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
            if (i % batchSize == 0) {
                // 等待消息发布确认
                boolean flag = channel.waitForConfirms();
                if (flag) {
                    log.info("{}条消息发送成功", batchSize);
                }
            }
        }

    }

    private static void publishIndividually() throws IOException, TimeoutException, InterruptedException {
        Channel channel = MqUtil.getChannel();

        String queueName = "publishIndividually";
        // durable 队列持久化
        channel.queueDeclare(queueName, true, false, true, null);

        // 单个发布确认
        channel.confirmSelect();

        for (int i = 0; i < MSG_COUNT; i++) {
            String msg = "" + i;
            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
            // 等待消息发布确认
            boolean flag = channel.waitForConfirms();
            if (flag) {
                log.info("消息发送成功");
            }
        }

    }


}
