package com.feyl.netty.advance.chat_room.message;

import lombok.Data;
import lombok.ToString;

/**
 * @author Feyl
 */
@Data
@ToString(callSuper = true)
public class GroupExitResponseMessage extends AbstractResponseMessage {
    public GroupExitResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return GroupExitResponseMessage;
    }
}
