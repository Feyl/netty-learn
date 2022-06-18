package com.feyl.netty.advance.chat_room.message;

/**
 * @author Feyl
 */
public class PongMessage extends Message {
    @Override
    public int getMessageType() {
        return PongMessage;
    }
}

