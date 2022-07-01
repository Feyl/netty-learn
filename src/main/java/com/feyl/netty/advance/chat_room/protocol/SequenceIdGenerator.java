package com.feyl.netty.advance.chat_room.protocol;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Feyl
 */
public class SequenceIdGenerator {
    private static final AtomicInteger id = new AtomicInteger();

    public static int nextId(){
        return id.incrementAndGet();
    }
}
