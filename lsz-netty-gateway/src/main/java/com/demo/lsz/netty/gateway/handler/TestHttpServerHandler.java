package com.demo.lsz.netty.gateway.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * HttpObject客户端和服务器端相互通讯的数据被封装成HttpObject
 */
@Component
@ChannelHandler.Sharable // 表示handler可以复用，不会出现竞态条件
@Slf4j
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    // 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            log.info("msg类型 = {}", msg.getClass());
            log.info("客户端地址 = {}", ctx.channel().remoteAddress());

            // 回复信息给浏览器
            ByteBuf content = Unpooled.copiedBuffer("Hello, I'm server", CharsetUtil.UTF_8);
            // 构造一个http的响应
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            // 将response发送
            ctx.writeAndFlush(response);
        }

    }

}
