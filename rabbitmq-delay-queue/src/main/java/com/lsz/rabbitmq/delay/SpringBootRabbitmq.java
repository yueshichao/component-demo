package com.lsz.rabbitmq.delay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class SpringBootRabbitmq {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRabbitmq.class, args);
    }

}
