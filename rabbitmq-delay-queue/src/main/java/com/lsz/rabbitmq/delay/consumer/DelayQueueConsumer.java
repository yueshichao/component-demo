package com.lsz.rabbitmq.delay.consumer;

import com.lsz.rabbitmq.delay.config.DelayQueueConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// 消费延迟队列的消息
@Slf4j
@Component
public class DelayQueueConsumer {

    @RabbitListener(queues = DelayQueueConfig.DELAY_QUEUE_NAME)
    public void consumerD(Message message, Channel channel) {
        String msg = new String(message.getBody());
        log.info("延迟队列消息 = {}", msg);
    }

}
