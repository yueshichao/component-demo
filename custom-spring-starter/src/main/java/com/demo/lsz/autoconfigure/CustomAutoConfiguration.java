package com.demo.lsz.autoconfigure;


import com.demo.lsz.prop.CustomProperties;
import com.demo.lsz.service.CustomService;
import com.demo.lsz.service.ProxyCustomService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CustomProperties.class)
public class CustomAutoConfiguration {

//    @Bean(initMethod = "init", destroyMethod = "destroy")
    @Bean
    public CustomService customService() {
        ProxyCustomService proxyCustomService = new ProxyCustomService();
        return proxyCustomService;
    }

}
