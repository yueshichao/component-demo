package com.demo.lsz.netty.gateway.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class SimpleChatChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final TestHttpServerHandler httpHandler;
//    private final HttpServerCodec httpServerCodec;

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());

        pipeline.addLast("MyTestHttpServerHandler", httpHandler);
    }
}
