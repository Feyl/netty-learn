package com.feyl.netty.bytebuffer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static com.feyl.netty.util.ByteBufferUtil.debugAll;

/**
 * @author Feyl
 * @date 2022/5/26 16:30
 */
public class ByteBufferString {
    public static void main(String[] args) {
        // 1. 字符串转为 ByteBuffer
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        buffer1.put("hello".getBytes());
        debugAll(buffer1);

        // 2. Charset：写完直接切换到 读模式
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello");
        debugAll(buffer2);

        // 3. wrap：将直接数组包装为 ByteBuffer，写完直接切换到 读模式
        ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes());
        debugAll(buffer3);

        // 4. 转为字符串
        String str1 = StandardCharsets.UTF_8.decode(buffer2).toString();
        System.out.println(str1);

//        buffer1.flip();
        String str2 = StandardCharsets.UTF_8.decode(buffer1).toString();
        System.out.println(str2);

    }
}
