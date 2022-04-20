package com.demo.lsz.service;


import com.demo.lsz.prop.CustomProperties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

public class CustomService {

    @Resource
    CustomProperties customProperties;

    public String handle(String name) {
        return customProperties.getPrefix() + name + customProperties.getSuffix();
    }


    @PostConstruct
    private void init() {
        System.out.println("===lsz CustomService init...");
    }

    @PreDestroy
    private void destroy() {
        System.out.println("===lsz CustomService destroy...");
    }

    public void afterPropertiesSet() {
        System.out.println("===lsz CustomService afterPropertiesSet...");
    }

}
