package com.demo.lsz.autoconfigure;


import com.demo.lsz.prop.CustomProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CustomProperties.class)
public class CustomAutoConfiguration {


}
