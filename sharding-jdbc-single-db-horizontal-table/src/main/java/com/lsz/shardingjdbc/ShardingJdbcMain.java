package com.lsz.shardingjdbc;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/*
* 参考：
* https://www.bilibili.com/video/BV1jJ411M78w
* https://github.com/yinjihuan/sharding-jdbc
*
* */

@SpringBootApplication
@Slf4j
@EnableSwagger2
public class ShardingJdbcMain {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcMain.class, args);
    }

}
