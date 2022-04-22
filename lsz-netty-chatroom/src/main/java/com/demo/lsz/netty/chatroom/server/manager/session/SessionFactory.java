package com.demo.lsz.netty.chatroom.server.manager.session;


import com.demo.lsz.netty.chatroom.config.SpringContextUtil;

public abstract class SessionFactory {

    public static Session getSession() {
        return SpringContextUtil.getBean(Session.class);
    }

}
