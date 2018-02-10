package ing_log_reader.commons.dto;

import ing_log_reader.commons.enums.MessageTypeEnum;

import java.io.Serializable;

public class MessageDTO implements Serializable{

    private MessageTypeEnum messageType;

    public MessageTypeEnum getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTypeEnum messageType) {
        this.messageType = messageType;
    }
}
