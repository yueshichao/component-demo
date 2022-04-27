package com.lsz.demo.factorybean.factory;

import com.lsz.demo.factorybean.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class UserFactoryBean implements FactoryBean<User> {

    @PostConstruct
    public void init() {
        log.info("{} init...", getClass().getSimpleName());
    }

    @Override
    public User getObject() throws Exception {
        log.info("{} getObject...", getClass().getSimpleName());
        return new User();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

}
