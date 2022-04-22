package com.demo.lsz.netty.chatroom.server.handler;

import com.demo.lsz.netty.chatroom.message.GroupCreateRequestMessage;
import com.demo.lsz.netty.chatroom.message.GroupCreateResponseMessage;
import com.demo.lsz.netty.chatroom.server.manager.group.Group;
import com.demo.lsz.netty.chatroom.server.manager.group.GroupSession;
import com.demo.lsz.netty.chatroom.server.manager.group.GroupSessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;
import java.util.Set;

/**
 * 创建群---管理器
 */
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {

        final String groupName = msg.getGroupName();
        final Set<String> members = msg.getMembers();

        // 群管理器
        final GroupSession groupSession = GroupSessionFactory.getGroupSession();
        final Group group = groupSession.createGroup(groupName, members);

        // 创建成功
        if (group == null) {
            // 发送成功消息
            ctx.writeAndFlush(new GroupCreateResponseMessage(true, "成功创建群聊:" + groupName));

            // 发送拉群消息
            final List<Channel> channels = groupSession.getMembersChannel(groupName);
            for (Channel ch : channels) {
                ch.writeAndFlush(new GroupCreateResponseMessage(true, "您已被拉入群聊:" + groupName));
            }
        }
        // 创建失败
        else {
            ctx.writeAndFlush(new GroupCreateResponseMessage(false, "已存在群: " + groupName));
        }
    }

}
