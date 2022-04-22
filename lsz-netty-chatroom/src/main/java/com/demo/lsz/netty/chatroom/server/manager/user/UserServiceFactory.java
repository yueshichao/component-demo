package com.demo.lsz.netty.chatroom.server.manager.user;


import com.demo.lsz.netty.chatroom.config.SpringContextUtil;

public abstract class UserServiceFactory {

    public static UserService getUserService() {
        return SpringContextUtil.getBean(UserService.class);
    }

}
