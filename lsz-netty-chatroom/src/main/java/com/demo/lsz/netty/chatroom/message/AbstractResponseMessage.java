package com.demo.lsz.netty.chatroom.message;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractResponseMessage extends Message {

    private boolean success;

    private String reason;

    @Override
    public int getSequenceId() {
        return 0;
    }



}
