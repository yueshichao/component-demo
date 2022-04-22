package com.demo.lsz.netty.chatroom.server.handler;


import com.alibaba.fastjson.JSON;
import com.demo.lsz.netty.chatroom.message.ChatRequestMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        log.debug("msg = {}", JSON.toJSONString(msg));
    }

}
