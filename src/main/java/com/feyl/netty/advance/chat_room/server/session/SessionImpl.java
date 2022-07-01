package com.feyl.netty.advance.chat_room.server.session;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Feyl
 */
public class SessionImpl implements Session{
    private final Map<String, Channel> userChannelStore = new ConcurrentHashMap<>();
    private final Map<Channel, String> channelUserStore = new ConcurrentHashMap<>();
    private final Map<Channel, Map<String, Object>> channelAttributesStore = new ConcurrentHashMap<>();

    @Override
    public void bind(Channel channel, String username) {
        userChannelStore.put(username, channel);
        channelUserStore.put(channel, username);
        channelAttributesStore.put(channel, new ConcurrentHashMap<>());
    }

    @Override
    public void unbind(Channel channel) {
        String username = channelUserStore.remove(channel);
        userChannelStore.remove(username);
        channelAttributesStore.remove(channel);
    }

    @Override
    public Object getAttribute(Channel channel, String name) {
        return channelAttributesStore.get(channel).get(name);
    }

    @Override
    public void setAttribute(Channel channel, String name, Object value) {
        channelAttributesStore.get(channel).put(name, value);
    }

    @Override
    public Channel getChannel(String username) {
        return userChannelStore.get(username);
    }

    @Override
    public String toString() {
        return userChannelStore.toString();
    }
}
