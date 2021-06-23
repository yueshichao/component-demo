package com.lsz.rabbitmq.produceack.consumer;

import com.lsz.rabbitmq.produceack.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME)
    public void receiveConfirmMsg(Message message) {
        log.info("{}接收到的消息 = {}", ConfirmConfig.CONFIRM_QUEUE_NAME, new String(message.getBody()));
    }

}
