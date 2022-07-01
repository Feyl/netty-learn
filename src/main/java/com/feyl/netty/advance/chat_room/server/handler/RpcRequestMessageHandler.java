package com.feyl.netty.advance.chat_room.server.handler;

import com.feyl.netty.advance.chat_room.message.RpcRequestMessage;
import com.feyl.netty.advance.chat_room.message.RpcResponseMessage;
import com.feyl.netty.advance.chat_room.server.service.RpcTestService;
import com.feyl.netty.advance.chat_room.server.service.ServiceFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Feyl
 */
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage msg) {
        RpcResponseMessage resp = new RpcResponseMessage();
        resp.setSequenceId(msg.getSequenceId());
        try {
            RpcTestService service =
                    (RpcTestService) ServiceFactory.getService(Class.forName(msg.getInterfaceName()));
            Method method = service.getClass().getMethod(msg.getMethodName(), msg.getParameterTypes());
            Object invoke = method.invoke(service, msg.getParameterValue());
            resp.setReturnValue(invoke);
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getCause().getMessage();
            resp.setExceptionValue(new Exception("远程调用出错：" + message));
        }
        ctx.writeAndFlush(resp);
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        RpcRequestMessage msg = new RpcRequestMessage(
                1,
                "com.feyl.netty.advance.chat_room.server.service.RpcTestService",
                "rpcTest",
                String.class,
                new Class[]{String.class},
                new Object[]{"This content is used to testing rpc"}
        );
        RpcTestService service =
                (RpcTestService) ServiceFactory.getService(Class.forName(msg.getInterfaceName()));
        Method method = service.getClass().getMethod(msg.getMethodName(), msg.getParameterTypes());
        Object invoke = method.invoke(service, msg.getParameterValue());
        System.out.println(invoke);
    }
}
