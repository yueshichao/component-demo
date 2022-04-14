package com.demo.lsz.prop;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "lsz.custom")
public class CustomProperties {

    private String prefix = "default";

    private String suffix = "default";

}
