package com.lsz.rabbitmq.produceack.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.lsz.rabbitmq.produceack.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/confirm")// 发布确认
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PutMapping("/send/{msg}")
    public String send(@PathVariable String msg) {

        String id = IdUtil.simpleUUID();
        CorrelationData correlationData = new CorrelationData(id);
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME, ConfirmConfig.ROUTING_KEY, msg, correlationData);
        log.info("发送消息内容 = {}", msg);
        return DateUtil.now();
    }

}
