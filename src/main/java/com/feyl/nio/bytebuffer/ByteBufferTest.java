package com.feyl.nio.bytebuffer;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Java IO流 RandomAccessFile:https://www.jianshu.com/p/360e37539266
 *
 * @author Feyl
 */
@Slf4j
public class ByteBufferTest {
    public static void main(String[] args) {
        //FileChannel
        //1. 输入输出流 2.RandomAccessFile
        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            //准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true) {
                // 从 channel 读取数据，向 buffer 写入
                int len = channel.read(buffer);
                log.debug("读取到的字节数 {}", len);
                if (len==-1) {//没有内容了
                    break;
                }
                // 打印 buffer 的内容
                buffer.flip();// 切换至读模式
                while(buffer.hasRemaining()) { //是否还有剩余未读数据
                    byte b = buffer.get();
                    log.debug("实际字节 {}", (char) b);
                }
                buffer.clear(); //切换为写模式
            }
        } catch (IOException e) {
        }
    }
}
