package com.feyl.netty.advance.chat_room.server.session;

/**
 * @author Feyl
 */
public class GroupSessionFactory {
    private static GroupSession session = new GroupSessionImpl();

    public static GroupSession getGroupSession() {
        return session;
    }
}
