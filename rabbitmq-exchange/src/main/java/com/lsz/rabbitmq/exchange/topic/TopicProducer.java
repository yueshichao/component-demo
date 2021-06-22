package com.lsz.rabbitmq.exchange.topic;

import com.lsz.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class TopicProducer {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String msg = sc.nextLine();
            channel.basicPublish(EXCHANGE_NAME, "a.orange.rabbit", null, msg.getBytes("UTF-8"));
            log.info("消息({})发送完毕", msg);
        }

    }

}
