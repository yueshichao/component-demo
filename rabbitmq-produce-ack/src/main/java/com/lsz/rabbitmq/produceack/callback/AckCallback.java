package com.lsz.rabbitmq.produceack.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;


/*
* RabbitTemplate.ConfirmCallback 交换机收到消息的回调，需配置publisher-confirms
* RabbitTemplate.ReturnCallback 消息无法投递到队列时回退的消息回调，需配置publisher-returns
 *
* */
@Slf4j
@Component
public class AckCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    // 注入到实例中
    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        // 当配置了备份交换机（alternate-exchange参数）时，投递失败的回调会走备份交换机
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = Optional.ofNullable(correlationData).map(CorrelationData::getId).orElse(null);
        if (ack) {
            log.info("交换机已收(id = {})消息", id);
        } else {
            log.warn("交换机未收到消息，原因：{}", cause);
        }
    }


    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("消息：{}，被交换机{}退回，退回原因：{}，路由key：{}", new String(message.getBody()), exchange, replyText, routingKey);
    }
}
