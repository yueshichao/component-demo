package com.demo.lsz.netty.gateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
* 参考：
*
* https://github.com/zbum/netty-spring-example
* https://github.com/YunaiV/SpringBoot-Labs/tree/master/lab-67
*
* */


@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class GateWayMain8050 {

    public static void main(String[] args) {
        SpringApplication.run(GateWayMain8050.class, args);
    }

}
