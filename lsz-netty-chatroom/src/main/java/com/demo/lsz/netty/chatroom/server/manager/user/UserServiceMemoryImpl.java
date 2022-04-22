package com.demo.lsz.netty.chatroom.server.manager.user;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Primary
public class UserServiceMemoryImpl implements UserService {

    @PostConstruct
    public void init() {
        System.out.println("UserServiceMemoryImpl init()");
    }

    @Override
    public boolean login(String username, String password) {

        final String pass = allUserMap.get(username);
        if (pass == null) {
            return false;
        }

        return pass.equals(password);
    }


    private Map<String, String> allUserMap = new ConcurrentHashMap<>();

    {
        allUserMap.put("zhangsan", "123");
        allUserMap.put("lisi", "123");
        allUserMap.put("wangwu", "123");
        allUserMap.put("zhaoliu", "123");
        allUserMap.put("qianqi", "123");
    }

}
