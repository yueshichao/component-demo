package com.lsz.springwebflux.config;

import com.lsz.springwebflux.service.IdGenerator;
import com.lsz.springwebflux.service.TraceGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;
import java.util.concurrent.atomic.LongAdder;

@Configuration
public class FluxConfiguration {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public TraceGenerator traceGenerator() {
        TraceGenerator traceGenerator = new TraceGenerator() {
            @Override
            public String traceId() {
                return UUID.randomUUID().toString().replace("-", "");
            }
        };
        return traceGenerator;
    }

    @Bean
    public IdGenerator idGenerator() {
        return new IdGenerator() {
            LongAdder idGenerator = new LongAdder();

            @Override
            public long getId() {
                idGenerator.increment();
                return idGenerator.longValue();
            }
        };
    }

}
