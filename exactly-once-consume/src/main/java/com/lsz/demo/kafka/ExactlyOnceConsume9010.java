package com.lsz.demo.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
*
* 参考：
* https://blog.csdn.net/u010634066/article/details/109803987
* https://segmentfault.com/a/1190000023555975
*
* */
@SpringBootApplication
@EnableSwagger2
public class ExactlyOnceConsume9010 {

    public static void main(String[] args) {
        SpringApplication.run(ExactlyOnceConsume9010.class, args);
    }

}
