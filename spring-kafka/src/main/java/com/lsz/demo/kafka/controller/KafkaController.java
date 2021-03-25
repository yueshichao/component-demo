package com.lsz.demo.kafka.controller;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Slf4j
public class KafkaController {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @PutMapping("/send")
    public String send(@RequestParam String msg) {
        kafkaTemplate.send("demoTopic", msg);
        return DateUtil.now() + " ===> " + msg;
    }

}
