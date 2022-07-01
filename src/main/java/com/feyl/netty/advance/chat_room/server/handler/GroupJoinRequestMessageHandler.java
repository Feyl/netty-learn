package com.feyl.netty.advance.chat_room.server.handler;

import com.feyl.netty.advance.chat_room.message.GroupJoinRequestMessage;
import com.feyl.netty.advance.chat_room.message.GroupJoinResponseMessage;
import com.feyl.netty.advance.chat_room.server.session.Group;
import com.feyl.netty.advance.chat_room.server.session.GroupSessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Feyl
 */
@ChannelHandler.Sharable
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage msg) throws Exception {
        Group group = GroupSessionFactory.getGroupSession().addMember(msg.getGroupName(), msg.getUsername());
        if (group != null) {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, msg.getGroupName() + " 群加入成功"));
        } else {
            ctx.writeAndFlush(new GroupJoinResponseMessage(false, msg.getGroupName()+ " 群不存在，加入失败"));
        }
    }
}
