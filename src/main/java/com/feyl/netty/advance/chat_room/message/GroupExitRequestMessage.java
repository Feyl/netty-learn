package com.feyl.netty.advance.chat_room.message;

import lombok.Data;
import lombok.ToString;

/**
 * @author Feyl
 */
@Data
@ToString(callSuper = true)
public class GroupExitRequestMessage extends Message {
    private String groupName;

    private String username;

    public GroupExitRequestMessage(String username, String groupName) {
        this.groupName = groupName;
        this.username = username;
    }

    @Override
    public int getMessageType() {
        return GroupExitRequestMessage;
    }
}
