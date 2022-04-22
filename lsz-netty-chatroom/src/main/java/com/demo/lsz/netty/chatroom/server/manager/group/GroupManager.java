package com.demo.lsz.netty.chatroom.server.manager.group;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class GroupManager implements GroupSession {


    @Override
    public Group createGroup(String name, Set<String> members) {
        return null;
    }

    @Override
    public Group joinMember(String name, String member) {
        return null;
    }

    @Override
    public Group removeMember(String name, String member) {
        return null;
    }

    @Override
    public Group removeGroup(String name) {
        return null;
    }

    @Override
    public Set<String> getMembers(String name) {
        return null;
    }

    @Override
    public List<Channel> getMembersChannel(String name) {
        return null;
    }
}
