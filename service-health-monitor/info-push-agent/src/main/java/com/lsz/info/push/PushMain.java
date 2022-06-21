package com.lsz.info.push;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 1. docker安装
 * docker run --name es6 -d -p 9200:9200 -p 9300:9300 elasticsearch:6.8.23
 * 2. 修改内存配置
 * vi /etc/sysctl.conf
 * vm.max_map_count=655360 # 添加配置
 * sysctl -p # 重启配置
 * <p>
 * <p>
 * 其他：
 * 各es版本对于type的处理：https://segmentfault.com/a/1190000040330218
 */

@SpringBootApplication
@Slf4j
public class PushMain {


    public static void main(String[] args) {

        SpringApplication.run(PushMain.class, args);

    }


}


