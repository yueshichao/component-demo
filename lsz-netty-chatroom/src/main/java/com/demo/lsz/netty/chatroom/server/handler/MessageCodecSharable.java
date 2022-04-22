package com.demo.lsz.netty.chatroom.server.handler;

import com.alibaba.fastjson.JSON;
import com.demo.lsz.netty.chatroom.message.Message;
import com.demo.lsz.netty.chatroom.protocol.MessageSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        ByteBuf buf = ctx.alloc().buffer();
        buf.writeBytes(new byte[]{1, 2, 3, 4});// 4B, 魔数
        buf.writeByte(1);// 1B, 版本
        buf.writeByte(0);// 1B, 序列化方式，0 - json，1 - java
        buf.writeByte(msg.getMessageType());// 1B, 消息类型
        buf.writeInt(msg.getSequenceId());// 4B, 请求序号，大端存储
        buf.writeByte(0xff);// 1B, 对齐填充

        byte[] bytes = MessageSerializer.serialze(msg);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);


        out.add(buf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        int magicNum = buf.readInt();
        byte version = buf.readByte();
        byte serializeType = buf.readByte();
        int messageType = buf.readByte();
        int sequenceId = buf.readInt();
        buf.readByte();// 1B, 对齐填充
        log.debug("magicNum = {}, version = {}, serializeType = {}, messageType = {}, sequenceId = {}"
                , magicNum, version, serializeType, messageType, sequenceId);

        int length = buf.readInt();
        byte[] bytes = new byte[length];
        buf.readBytes(bytes, 0, length);

        Message message = MessageSerializer.deserialze(Message.getMessageClass(messageType), bytes);
        log.debug("message = {}", JSON.toJSONString(message));

        out.add(message);
    }


}
