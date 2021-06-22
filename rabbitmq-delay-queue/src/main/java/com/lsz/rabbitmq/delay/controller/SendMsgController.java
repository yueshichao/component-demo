package com.lsz.rabbitmq.delay.controller;

import cn.hutool.core.date.DateUtil;
import com.lsz.rabbitmq.delay.config.DelayQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PutMapping("/send/{msg}")
    public String send(@PathVariable String msg) {
        log.info("发送消息：{}", msg);
        rabbitTemplate.convertAndSend("X", "XA", "消息来自ttl为10s的队列：" + msg);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自ttl为40s的队列：" + msg);
        return DateUtil.now();
    }

    @PutMapping("/send/{expireSecond}/{msg}")
    public String send(@PathVariable Integer expireSecond, @PathVariable String msg) {
        log.info("发送消息：{}，过期时间：{}s", msg, expireSecond);
        rabbitTemplate.convertAndSend("X", "XC", msg, message -> {
            // 在这设置发送消息的过期时间
            message.getMessageProperties().setExpiration(expireSecond * 1000 + "");
            /*
            * 但这种方式存在问题，比如第一条消息是10s，第二条消息是1s
            * rabbitmq只会等待第一条消息过期才会检查第二条消息，所以第二条消息是第11s发给死信队列的
            *
            * */
            return message;
        });
        return DateUtil.now();
    }


    @PutMapping("/v2/send/{expireSecond}/{msg}")
    public String sendV2(@PathVariable Integer expireSecond, @PathVariable String msg) {
        log.info("发送消息：{}，过期时间：{}s", msg, expireSecond);
        // 发送给延迟队列
        rabbitTemplate.convertAndSend(DelayQueueConfig.DELAY_EXCHANGE_NAME, DelayQueueConfig.DELAY_ROUTING_KEY, msg, message -> {
            // 在这设置发送消息的过期时间
            message.getMessageProperties().setDelay(expireSecond * 1000);
            return message;
        });
        return DateUtil.now();
    }


}
