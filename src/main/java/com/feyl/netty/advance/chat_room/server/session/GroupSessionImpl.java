package com.feyl.netty.advance.chat_room.server.session;

import io.netty.channel.Channel;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Feyl
 */
public class GroupSessionImpl implements  GroupSession{
    private final Map<String, Group> groups = new ConcurrentHashMap<>();


    @Override
    public Group createGroup(String name, Set<String> members) {
        Group group = new Group(name, members);
        return groups.putIfAbsent(name, group);
    }

    @Override
    public Group addMember(String name, String member) {
        return groups.computeIfPresent(name, (k, v) -> {
            v.getMembers().add(member);
            return v;
        });
    }

    @Override
    public Group removeMember(String name, String member) {
        return groups.computeIfPresent(name, (k, v) -> {
            v.getMembers().remove(member);
            return v;
        });
    }

    @Override
    public Group removeGroup(String name) {
        return groups.remove(name);
    }

    @Override
    public Set<String> getMembers(String name) {
        return groups.getOrDefault(name, Group.EMPTY_GROUP).getMembers();
    }

    @Override
    public List<Channel> getMembersChannel(String name) {
        return getMembers(name).stream()
                .map(member -> SessionFactory.getSession().getChannel(member))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
