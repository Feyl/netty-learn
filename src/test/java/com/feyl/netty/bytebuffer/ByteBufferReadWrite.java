package com.feyl.netty.bytebuffer;

import java.nio.ByteBuffer;

import static com.feyl.netty.util.ByteBufferUtil.debugAll;

/**
 * position：读写起始位置
 * limit：读写限制
 * capacity：buffer缓冲区大小
 *
 * @author Feyl
 * @date 2022/5/26 15:54
 */
public class ByteBufferReadWrite {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x61); // 'a'
        debugAll(buffer);

        buffer.put(new byte[]{0x62, 0x63, 0x64}); // b  c  d
        debugAll(buffer);

//        System.out.println(buffer.get());
        buffer.flip();//读模式
        System.out.println(buffer.get());
        debugAll(buffer);

        buffer.compact(); //写模式
        debugAll(buffer);

        buffer.put(new byte[]{0x65, 0x66});
        debugAll(buffer);
    }
}
