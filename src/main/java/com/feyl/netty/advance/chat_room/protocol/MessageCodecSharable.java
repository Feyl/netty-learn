package com.feyl.netty.advance.chat_room.protocol;

import com.feyl.netty.advance.chat_room.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;

/**
 * 可共享的编解码器
 * 必须和 LengthFieldBasedFrameDecoder 一起使用，确保接到的 ByteBuf 消息是完整的
 *
 * @author Feyl
 */
@Slf4j
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {
    private final byte[] MAGIC_NUMBER = new byte[]{'f', 'l', 'o', 'w'};

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> list) throws Exception {
        ByteBuf out = ctx.alloc().buffer();
        //1、 4字节的魔数
        out.writeBytes(MAGIC_NUMBER);
        //2、 1字节的版本
        out.writeByte(1);
        //3、 1字节的序列化方式 jdk 0，json 1
        out.writeByte(0);
        //4、 1字节的指令类型
        out.writeByte(msg.getMessageType());
        //5、 4字节序列化号码
        out.writeInt(msg.getSequenceId());
        //6、 无意义，对齐填充
        out.writeByte(0xff);
        //7、 获取内容的字节数组
        byte[] content = MessageToBytes(msg);
        out.writeInt(content.length);
        out.writeBytes(content);
        list.add(out);
    }

    private byte[] MessageToBytes(Message msg) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        byte[] bytes = bos.toByteArray();
        return bytes;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magicNumber = in.readInt();
        byte version = in.readByte();
        byte serializerType = in.readByte();
        byte messageType = in.readByte();
        int sequenceId = in.readInt();
        in.readByte();
        Message message = BytesToMessage(in);
        log.debug("magic number: {}, version:{}, serializer type:{}, message type:{}, sequence id:{}", magicNumber, version, serializerType, messageType, sequenceId);
        log.debug("message:{}", message);
        out.add(message);
    }

    private Message BytesToMessage(ByteBuf in) throws IOException, ClassNotFoundException {
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Message message = (Message) ois.readObject();
        log.debug("length:{}", length);
        return message;
    }
}
