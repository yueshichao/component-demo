package com.lsz.demo.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic initialTopic() {
        return new NewTopic("demoTopic", 8, (short) 1);
    }

    @Bean
    public NewTopic updateTopic() {
        return new NewTopic("demoTopic", 10, (short) 1);
    }


}
