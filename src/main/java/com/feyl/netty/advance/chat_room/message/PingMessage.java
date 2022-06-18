package com.feyl.netty.advance.chat_room.message;

/**
 * @author Feyl
 */
public class PingMessage extends Message {
    @Override
    public int getMessageType() {
        return PingMessage;
    }
}

