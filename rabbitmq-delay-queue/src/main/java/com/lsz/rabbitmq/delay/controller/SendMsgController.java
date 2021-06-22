package com.lsz.rabbitmq.delay.controller;

import cn.hutool.core.date.DateUtil;
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


}
