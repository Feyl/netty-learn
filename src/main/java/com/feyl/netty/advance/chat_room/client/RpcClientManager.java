package com.feyl.netty.advance.chat_room.client;

import com.feyl.netty.advance.chat_room.client.handler.RpcResponseMessageHandler;
import com.feyl.netty.advance.chat_room.message.RpcRequestMessage;
import com.feyl.netty.advance.chat_room.protocol.MessageCodecSharable;
import com.feyl.netty.advance.chat_room.protocol.ProtocolFrameDecoder;
import com.feyl.netty.advance.chat_room.protocol.SequenceIdGenerator;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

/**
 * @author Feyl
 */
@Slf4j
public class RpcClientManager {

    public static <T> T getProxyService(Class<T> cls) {
        ClassLoader loader = cls.getClassLoader();
        Class<?>[] interfaces = new Class[]{cls};

        Object o = Proxy.newProxyInstance(loader, interfaces, (proxy, method, args) -> {
            // 1.将方法调用转换为 消息对象
            int sequenceId = SequenceIdGenerator.nextId();
            RpcRequestMessage msg = new RpcRequestMessage(
                    sequenceId,
                    cls.getName(),
                    method.getName(),
                    method.getReturnType(),
                    method.getParameterTypes(),
                    args
            );
            // 2. 将消息对象发送出去
            getChannel().writeAndFlush(msg);

            // 3. 准备一个空 Promise 对象，来接收结果                指定 promise 对象异步接收结果线程
            DefaultPromise<Object> promise = new DefaultPromise<>(getChannel().eventLoop());
            RpcResponseMessageHandler.PROMISES.put(sequenceId, promise);

//            promise.addListener(future -> {
//                // 线程
//            });

            //4. 等待 promise 结果
            promise.await();
            if (promise.isSuccess()) {
                // 调用正常
                return promise.getNow();
            } else {
                // 调用异常
                throw new RuntimeException(promise.cause());
            }
        });
        return (T) o;
    }


    private static Channel channel = null;
    private static final Object LOCK = new Object();

    // 获取唯一的 channel 对象
    public static Channel getChannel() {
        if (channel != null) {
            return channel;
        }
        synchronized (LOCK) {
            if (channel != null) {
                return channel;
            }
            initChannel();
            return channel;
        }
    }

    // 初始化 channel 方法
    private static void initChannel() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        RpcResponseMessageHandler RPC_HANDLER = new RpcResponseMessageHandler();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(group);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel sc) throws Exception {
                sc.pipeline().addLast(new ProtocolFrameDecoder());
                sc.pipeline().addLast(LOGGING_HANDLER);
                sc.pipeline().addLast(MESSAGE_CODEC);
                sc.pipeline().addLast(RPC_HANDLER);
            }
        });
        try {
            bootstrap.connect("localhost", 8080).sync().channel();
            channel.closeFuture().addListener(future -> {
                group.shutdownGracefully();
            });
        } catch (InterruptedException e) {
            log.error("rpc client error", e);
        }
    }
}
