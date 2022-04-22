package com.demo.lsz.netty.chatroom.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChatResponseMessage extends AbstractResponseMessage {

    private String from;
    private String content;


    @Override
    public int getMessageType() {
        return ChatResponseMessage;
    }

}
