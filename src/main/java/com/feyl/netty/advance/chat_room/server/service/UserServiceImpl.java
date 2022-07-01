package com.feyl.netty.advance.chat_room.server.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Feyl
 */
public class UserServiceImpl implements UserService{

    private Map<String, String> userStore = new ConcurrentHashMap<>();

    {
        userStore.put("feyl","awesome");
    }


    @Override
    public boolean login(String username, String password) {
        String pass = userStore.get(username);
        return pass == null ? false : pass.equals(password);
    }
}
