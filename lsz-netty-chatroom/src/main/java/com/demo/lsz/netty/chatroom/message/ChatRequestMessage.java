package com.demo.lsz.netty.chatroom.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestMessage extends Message {

    private String from;
    private String to;
    private String content;

    @Override
    public int getMessageType() {
        return ChatRequestMessage;
    }

}
