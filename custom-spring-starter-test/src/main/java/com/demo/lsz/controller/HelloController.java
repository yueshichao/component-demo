package com.demo.lsz.controller;

import com.demo.lsz.service.CustomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class HelloController {

    @Resource
    CustomService customService;

    @GetMapping("/demo/{name}")
    public String demo(@PathVariable String name) {
        return customService.handle(name);
    }

}
