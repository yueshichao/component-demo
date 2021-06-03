package com.lsz.demo.kafka.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.lsz.demo.kafka.dto.MsgDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ProducerController {

    public static final String topic = "demoTopic";

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    private final static Snowflake snowflake = IdUtil.createSnowflake(0, 0);

    @PutMapping("/send")
    public String send(@RequestParam String msg) {
        MsgDTO msgDTO = new MsgDTO(snowflake.nextIdStr(), msg);
        kafkaTemplate.send(topic, msgDTO.toString());
        kafkaTemplate.send(topic, msgDTO.toString());
        return DateUtil.now() + " ===> " + msg;
    }



}
