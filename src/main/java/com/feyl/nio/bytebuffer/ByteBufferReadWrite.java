package com.feyl.nio.bytebuffer;

import com.feyl.nio.util.ByteBufferUtil;

import java.nio.ByteBuffer;

/**
 * position：读写起始位置
 * limit：读写限制
 * capacity：buffer缓冲区大小
 *
 * @author Feyl
 */
public class ByteBufferReadWrite {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x61); // 'a'
        ByteBufferUtil.debugAll(buffer);

        buffer.put(new byte[]{0x62, 0x63, 0x64}); // b  c  d
        ByteBufferUtil.debugAll(buffer);

//        System.out.println(buffer.get());
        buffer.flip();//读模式
        System.out.println(buffer.get());
        ByteBufferUtil.debugAll(buffer);

        buffer.compact(); //写模式
        ByteBufferUtil.debugAll(buffer);

        buffer.put(new byte[]{0x65, 0x66});
        ByteBufferUtil.debugAll(buffer);
    }
}
