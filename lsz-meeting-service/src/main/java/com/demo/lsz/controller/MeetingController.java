package com.demo.lsz.controller;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/meeting")
@Slf4j
public class MeetingController {

    @GetMapping("/test")
    public String demo() {
        return "meeting test ===> " + DateUtil.now();
    }

}
