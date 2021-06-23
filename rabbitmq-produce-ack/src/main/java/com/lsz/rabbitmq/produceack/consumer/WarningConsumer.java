package com.lsz.rabbitmq.produceack.consumer;

import com.lsz.rabbitmq.produceack.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WarningConsumer {

    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE_NAME)
    public void receiveConfirmMsg(Message message) {
        log.info("报警队列接收到的消息 = {}", new String(message.getBody()));
    }

}
