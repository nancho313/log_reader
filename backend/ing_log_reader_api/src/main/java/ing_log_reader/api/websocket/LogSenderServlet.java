package ing_log_reader.api.websocket;

import com.google.gson.Gson;
import ing_log_reader.api.handler.LogConsumerHandler;
import ing_log_reader.commons.dto.UserCriteriaDTO;
import ing_log_reader.commons.dto.ReadDTO;
import ing_log_reader.commons.dto.SSHConfigManagerDTO;

import ing_log_reader.commons.enums.CriteriaEnum;
import ing_log_reader.commons.enums.ResultTypeEnum;
import ing_log_reader.commons.interfaces.IReaderPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/LogSender")
public class LogSenderServlet implements IReaderPrincipal {

    private Map<String, LogConsumerHandler> currentUsers;

    private Gson gson = new Gson();

    private static final Logger logger = LoggerFactory.getLogger(LogSenderServlet.class);

    @OnOpen
    public void onOpen(Session session) {

        this.getCurrentUsers().put(session.getId(), new LogConsumerHandler(this, session));
    }

    @OnMessage
    public void onMessage(String message, Session session){

        logger.info("[onMessage] message -> {}", message);

        LogConsumerHandler consumer = this.getCurrentUsers().get(session.getId());

        if(consumer.getReaderController() != null){

            consumer.getReaderController().closeRead();
        }

        SSHConfigManagerDTO configManagerDTO = this.getGson().fromJson(message, SSHConfigManagerDTO.class);

        logger.info("[onMessage] configManagerDTO -> {}", configManagerDTO);

        configManagerDTO.getUserCriteriaDTO().setIdSession(session.getId());

        consumer.getReaderController().startRead(configManagerDTO);
    }

    @Override
    public void sendContentReads(ReadDTO contentReads, String idSession) {

        LogConsumerHandler consumer = this.getCurrentUsers().get(idSession);

        String message = this.getGson().toJson(contentReads);

        try {
            consumer.getSession().getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason){

        logger.info("[onClose] session.getId() -> {}, closeReason -> {}", session.getId(), closeReason);

        LogConsumerHandler consumer = this.getCurrentUsers().get(session.getId());

        consumer.getReaderController().closeRead();
    }

    public Map<String, LogConsumerHandler> getCurrentUsers() {

        if(currentUsers == null){
            currentUsers = new HashMap<String, LogConsumerHandler>();
        }

        return currentUsers;
    }

    public Gson getGson() {
        return gson;
    }
}
