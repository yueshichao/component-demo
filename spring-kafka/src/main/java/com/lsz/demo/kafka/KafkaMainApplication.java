package com.lsz.demo.kafka;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// 参考：https://blog.csdn.net/yuanlong122716/article/details/105160545/

/*
Docker 配置Kafka环境
1. docker run -d --name zk -p 2181:2181 wurstmeister/zookeeper
2. docker run -d --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=zk-host:2181/kafka -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka-host:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 wurstmeister/kafka
关于第2步，一定要注意，我在家配环境时，新下载了kafka客户端工具Offset Explorer(之前叫Kafka Tool)
之后发现zookeeper毫无问题，客户端也连得上，但是kafka怎么也连不上，一开始以为是Offset Explorer的问题
但网上找不到Kafka Tool的安装包了，后来再排查，怀疑是KAFKA_ADVERTISED_LISTENERS这个配置的问题
用了别人的docker-composed.yml也是一样
也怀疑是kafka版本问题，用docker卸载了2.7，安装了2.3版本，还是一样
最后查了一下午才发现是宿主机防火墙开了，导致容器连不上宿主机网络


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
