package com.feyl.netty.component.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;


/**
 * @author Feyl
 */
public class SliceTest {

    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
        BufUtil.log(buf);

        // 在切片过程中，没有发生数据复制
        ByteBuf f1 = buf.slice(0, 5);
        f1.retain();
        // 'a','b','c','d','e', 'x'
        ByteBuf f2 = buf.slice(5, 5);
        f2.retain();
        BufUtil.log(f1);
        BufUtil.log(f2);

        System.out.println("释放原有 byteBuf 内存");
        buf.release();
        BufUtil.log(f1);


        f1.release();
        f2.release();
        /*System.out.println("========================");
        f1.setByte(0, 'b');
        BufUtil.log(f1);
        BufUtil.log(buf);*/
    }

}
