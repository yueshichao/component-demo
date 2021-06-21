package com.lsz.rabbitmq.exchange.direct;

import com.lsz.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class DirectProducer {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String msg = sc.nextLine();
            channel.basicPublish(EXCHANGE_NAME, "error", null, msg.getBytes("UTF-8"));
            log.info("消息({})发送完毕", msg);
        }
    }


}
