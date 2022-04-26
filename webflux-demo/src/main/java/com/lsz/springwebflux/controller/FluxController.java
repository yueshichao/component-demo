package com.lsz.springwebflux.controller;

import com.lsz.springwebflux.service.IdGenerator;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
@RequestMapping("/flux")
public class FluxController {

    @Resource(name = "idGenerator")
    IdGenerator idGenerator;

    @Resource
    WebClient webClient;

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


}
