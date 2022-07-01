package com.feyl.netty.advance.chat_room.server.handler;

import com.feyl.netty.advance.chat_room.message.GroupMembersRequestMessage;
import com.feyl.netty.advance.chat_room.message.GroupMembersResponseMessage;
import com.feyl.netty.advance.chat_room.server.session.GroupSessionFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Set;

/**
 * @author Feyl
 */
public class GroupMembersRequestMessageHandler extends SimpleChannelInboundHandler<GroupMembersRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMembersRequestMessage msg) throws Exception {
        Set<String> members = GroupSessionFactory.getGroupSession().getMembers(msg.getGroupName());
        ctx.writeAndFlush(new GroupMembersResponseMessage(members));
    }
}
