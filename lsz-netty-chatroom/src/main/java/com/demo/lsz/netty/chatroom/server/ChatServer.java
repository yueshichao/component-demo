package com.demo.lsz.netty.chatroom.server;

import com.demo.lsz.netty.chatroom.protocol.ProtocolFrameDecoder;
import com.demo.lsz.netty.chatroom.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class ChatServer {

    @PostConstruct
    public void init() {
        log.info("{} start...", getClass().getSimpleName());
        start();
    }

    public void start() {

        log.info("chat server start...");
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();

        try {
            ServerBootstrap bs = new ServerBootstrap();
            bs.channel(NioServerSocketChannel.class);
            bs.group(boss, work);

            bs.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new QuitHandler());
                    ch.pipeline().addLast(new ProtocolFrameDecoder());
                    ch.pipeline().addLast(new LoggingHandler());
                    ch.pipeline().addLast(new MessageCodecSharable());
                    ch.pipeline().addLast(new LoginRequestMessageHandler());
                    ch.pipeline().addLast(new ChatRequestMessageHandler());
                    ch.pipeline().addLast(new GroupCreateRequestMessageHandler());
                    ch.pipeline().addLast(new GroupChatRequestMessageHandler());
                }

            });
            ChannelFuture channelFuture = bs.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("{}", e);
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }


    }

}
