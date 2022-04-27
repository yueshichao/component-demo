package com.lsz.demo.factorybean;

import com.lsz.demo.factorybean.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SpringBootApplication
@Slf4j
public class FactoryBeanDemoApplication implements ApplicationContextAware {

    public static void main(String[] args) {
        SpringApplication.run(FactoryBeanDemoApplication.class, args);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        User bean1 = applicationContext.getBean(User.class);
        User bean2 = applicationContext.getBean(User.class);
        log.info("bean = {}", bean1);
        log.info("bean = {}", bean2);
    }
}
