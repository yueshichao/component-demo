package com.demo.lsz.netty.gateway;

import com.demo.lsz.netty.gateway.server.GatewayServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

/*
* 参考：https://github.com/zbum/netty-spring-example
*
* */


@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class GateWayMain8050 {

    public static void main(String[] args) {
        SpringApplication.run(GateWayMain8050.class, args);
    }

    private final GatewayServer gatewayServer;

    @Bean
    public ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener() {
        return new ApplicationListener<ApplicationReadyEvent>() {
            @Override
            public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
                log.info("Spring启动成功~");
                gatewayServer.start();
            }
        };
    }

}
