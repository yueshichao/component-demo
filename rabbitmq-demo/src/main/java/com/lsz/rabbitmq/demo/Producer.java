package com.lsz.rabbitmq.demo;

import com.lsz.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.lsz.rabbitmq.util.MqUtil.QUEUE_NAME;

@Slf4j
public class Producer {


    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = MqUtil.getChannel();

        /*
        * 生成一个队列
        * 1. 队列名称
        * 2. 是否持久化
        * 3. 是否可供多个消费者消费
        * 4. 是否自动删除
        * 5. 其他参数
        *
        * */
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);


        String msg = "hello, world";
        /*
        * 1. 发送到哪个交换机
        * 2. 路由的key是哪个，本次队列的名称
        * 3. 其他参数信息
        * 4. 消息内容
        * */
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        log.info("消息发送完毕...");

    }

}
