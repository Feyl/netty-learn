package com.feyl.nio.bytebuffer;

import java.nio.ByteBuffer;

/**
 * ByteBuffer类型
 *  - class java.nio.HeapByteBuffer：    java 堆内存，读写效率较低，受到 GC 的影响
 *  - class java.nio.DirectByteBuffer：  直接内存，读写效率高（少一次拷贝），不会受 GC 影响，分配的效率低（原因：需要调用操作系统的函数进行分配）
 *
 * @author Feyl
 */
public class ByteBufferAllocate {
    public static void main(String[] args) {
        System.out.println(ByteBuffer.allocate(16).getClass());
        System.out.println(ByteBuffer.allocateDirect(16).getClass());
    }
}
