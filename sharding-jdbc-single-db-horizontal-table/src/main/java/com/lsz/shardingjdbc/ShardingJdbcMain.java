package com.lsz.shardingjdbc;


import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/*
* 参考：
* https://www.bilibili.com/video/BV1jJ411M78w
* https://github.com/yinjihuan/sharding-jdbc
*
* */

@SpringBootApplication(exclude = /*注意这里不要引错了，要exclude的是shardingsphere包下的配置*/SpringBootConfiguration.class)
@Slf4j
@EnableSwagger2
public class ShardingJdbcMain {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcMain.class, args);
    }

}
