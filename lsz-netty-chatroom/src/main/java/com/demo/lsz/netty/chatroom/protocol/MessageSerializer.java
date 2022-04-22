package com.demo.lsz.netty.chatroom.protocol;

import com.alibaba.fastjson.JSON;
import com.demo.lsz.netty.chatroom.message.Message;

import java.nio.charset.StandardCharsets;


public class MessageSerializer {

    public static byte[] serialze(Message msg) {
        return JSON.toJSONString(msg).getBytes(StandardCharsets.UTF_8);
    }


    public static Message deserialze(Class<? extends Message> messageClazz, byte[] bytes) {
        String json = new String(bytes, StandardCharsets.UTF_8);
        Message message = JSON.parseObject(json, messageClazz);
        return message;
    }

}
