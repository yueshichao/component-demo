package com.demo.lsz.netty.chatroom.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PingMessage extends Message {

    @Override
    public int getMessageType() {
        return PingMessage;
    }

}
