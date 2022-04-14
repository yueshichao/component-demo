package com.demo.lsz.service;


import com.demo.lsz.prop.CustomProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CustomService {

    @Resource
    CustomProperties customProperties;

    public String handle(String name) {
        return customProperties.getPrefix() + name + customProperties.getSuffix();
    }


}
