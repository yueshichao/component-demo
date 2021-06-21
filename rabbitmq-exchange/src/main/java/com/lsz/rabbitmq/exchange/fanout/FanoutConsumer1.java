package com.lsz.rabbitmq.exchange.fanout;

import com.lsz.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FanoutConsumer1 {

    private static final String EXCHANGE_NAME = "logs";


    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();
        // 声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        /*
        * 临时队列，名称随机，连接断开队列自动删除
        * */
        String queueName = channel.queueDeclare().getQueue();

        // 绑定交换机和队列
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        log.info("等待接收消息...");
        DeliverCallback deliverCallback = (consumerTag, msg) -> {
            log.info("FanoutConsumer1消息：{}", new String(msg.getBody()));
        };

        channel.basicConsume(queueName, true, deliverCallback, (consumerTag) -> {});


    }

}
