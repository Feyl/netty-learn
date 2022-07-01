package com.feyl.netty.advance.chat_room.server.service;

/**
 * @author Feyl
 */
public class RpcTestServiceImpl implements RpcTestService{
    @Override
    public String rpcTest(String msg) {
        int i = 1 / 0;
        return "content: " + msg;
    }
}
