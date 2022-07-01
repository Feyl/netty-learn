package com.feyl.netty.advance.chat_room.server.session;

/**
 * @author Feyl
 */
public class SessionFactory {

    private static Session session = new SessionImpl();

    public static Session getSession(){
        return session;
    }
}
