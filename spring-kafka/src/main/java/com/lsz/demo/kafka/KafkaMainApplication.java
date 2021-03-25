package com.lsz.demo.kafka;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// 参考：https://blog.csdn.net/yuanlong122716/article/details/105160545/

/*
Docker 配置Kafka环境
1. docker run -d -p 2181:2181 zookeeper
2. docker run -d --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=zk-host:2181/kafka -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://zk-host:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 wurstmeister/kafka

Spring与Kafka整合需要版本对应
参考：https://spring.io/projects/spring-kafka

但上面的参考我试了没用，以下方法不知道为什么生效的
https://blog.csdn.net/xue15029240296/article/details/103508643

* */


@SpringBootApplication
@EnableSwagger2
public class KafkaMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaMainApplication.class, args);
    }

}
