package com.demo.lsz.netty.chatroom.server.manager.group;


import com.demo.lsz.netty.chatroom.config.SpringContextUtil;

public abstract class GroupSessionFactory {

    public static GroupSession getGroupSession() {
        return SpringContextUtil.getBean(GroupSession.class);
    }

}
