package com.lsz.rabbitmq.delay.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeadLetterQueueConsumer {

    @RabbitListener(queues = "QD")
    public void consumerD(Message message, Channel channel) {
        String msg = new String(message.getBody());
        log.info("死信消息 = {}", msg);
    }

}
