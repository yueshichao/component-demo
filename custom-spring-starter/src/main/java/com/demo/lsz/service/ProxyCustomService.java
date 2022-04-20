package com.demo.lsz.service;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProxyCustomService extends CustomService {

    @Override
    public String handle(String name) {
        log.info("CustomService#handle(), name = {}", name);
        String handle = super.handle(name);
        log.info("CustomService#handle(), result = {}", handle);
        return handle;
    }

}
