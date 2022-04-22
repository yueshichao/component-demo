package com.demo.lsz.netty.chatroom.server.handler;

import com.demo.lsz.netty.chatroom.message.GroupChatRequestMessage;
import com.demo.lsz.netty.chatroom.message.GroupChatResponseMessage;
import com.demo.lsz.netty.chatroom.server.manager.group.GroupSession;
import com.demo.lsz.netty.chatroom.server.manager.group.GroupSessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * 群聊---管理器
 */
@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {

        final GroupSession groupSession = GroupSessionFactory.getGroupSession();
        final List<Channel> channelList = groupSession.getMembersChannel(msg.getGroupName());

        for (Channel channel : channelList) {
            channel.writeAndFlush(new GroupChatResponseMessage(msg.getFrom(), msg.getContent()));
        }

    }

}
