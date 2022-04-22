package com.demo.lsz.netty.chatroom.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class ChatRoomConfiguration {

    @PostConstruct
    public void init() {
        log.info("{} start...", getClass().getSimpleName());
    }

}
