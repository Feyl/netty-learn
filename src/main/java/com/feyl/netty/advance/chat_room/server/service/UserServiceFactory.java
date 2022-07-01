package com.feyl.netty.advance.chat_room.server.service;

/**
 * @author Feyl
 */
public abstract class UserServiceFactory {
    //单例模式：饿汉式
    private static UserService userService = new UserServiceImpl();

    public static UserService getUserService() {
        return userService;
    }
}
