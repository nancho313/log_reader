package ing_log_reader.api.message;

import com.google.gson.Gson;
import ing_log_reader.commons.dto.CloseLogDTO;
import ing_log_reader.commons.dto.MessageDTO;
import ing_log_reader.commons.dto.SSHConfigManagerDTO;

public enum MessageResolver {

    INSTANCE;

    public MessageDTO resolveMessage(String message){

        Gson gson = new Gson();

        MessageDTO messageDTO = gson.fromJson(message, MessageDTO.class);

        switch (messageDTO.getMessageType()){

            case CLOSE_LOG:

                messageDTO = gson.fromJson(message, CloseLogDTO.class);
                break;

            case REQUEST_LOG:

                messageDTO = gson.fromJson(message, SSHConfigManagerDTO.class);
                break;
        }

        return messageDTO;
    }
}
