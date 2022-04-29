package com.lsz.springwebflux.controller;

import com.lsz.springwebflux.service.IdGenerator;
import com.lsz.springwebflux.trace.MyTracer;
import com.lsz.springwebflux.trace.MyTracerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
@RequestMapping("/flux")
@Slf4j
public class FluxController {

    @Resource(name = "idGenerator")
    IdGenerator idGenerator;

    @Resource
    WebClient webClient;

    @Resource(name = "threadPool")
    ThreadPoolTaskExecutor threadPool;

    @RequestMapping("/get/id")
    public Mono<Long> getId() {

        return Mono.just(idGenerator.getId());
    }

    @RequestMapping("/proxy/{uri}")
    public Mono<String> proxyDemo(@PathVariable String uri) {
        return doProxyDemo(uri);
    }

    private Mono<String> doProxyDemo(String uri) {
        WebClient.RequestBodySpec req = webClient.method(HttpMethod.GET)
                .uri("http://127.0.0.1:9898/" + uri);
        Mono<ClientResponse> responseMono = req.exchange();
        return responseMono.flatMap((resp) -> {
            return resp.bodyToMono(String.class);
        });
    }

    @RequestMapping("/trace")
    public Mono<Void> trace() {
        MyTracer tracer = MyTracerContext.createIfAbsent();
        try {
            log.info("main trace = {}", tracer);
            threadPool.execute(() -> {
                MyTracer subTracer = MyTracerContext.createIfAbsent();
                log.info("executor trace = {}", subTracer);
            });
        } finally {
            MyTracerContext.remove();
        }
        return Mono.empty();
    }


}
