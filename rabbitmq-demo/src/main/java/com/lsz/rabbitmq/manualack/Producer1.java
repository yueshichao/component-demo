package com.lsz.rabbitmq.manualack;

import com.lsz.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static com.lsz.rabbitmq.util.MqUtil.QUEUE_NAME;

@Slf4j
public class Producer1 {

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = MqUtil.getChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, true, null);


        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String msg = sc.nextLine();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            log.info("消息发送完毕...");
        }

    }


}
