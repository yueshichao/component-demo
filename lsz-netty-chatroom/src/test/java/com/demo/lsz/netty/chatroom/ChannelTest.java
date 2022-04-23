package com.demo.lsz.netty.chatroom;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ChannelTest {

    @Test
    public void test() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new LoggingHandler());
        embeddedChannel.writeInbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("hello".getBytes()));
        Object o = embeddedChannel.readOutbound();
        log.info("o = {}", o);
    }


}
