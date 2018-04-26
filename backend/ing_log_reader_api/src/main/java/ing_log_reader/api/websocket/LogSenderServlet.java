package ing_log_reader.api.websocket;

import com.google.gson.Gson;
import ing_log_reader.api.handler.LogConsumerHandler;
import ing_log_reader.api.message.MessageResolver;
import ing_log_reader.commons.dto.*;

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

//Este es un pequenio cambio para que jenkins pueda volver a construir
// Agrego otro cambio

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

        MessageDTO messageDTO = MessageResolver.INSTANCE.resolveMessage(message);

        if(messageDTO instanceof CloseLogDTO){

            closeLog(session);
        }
        else
            if(messageDTO instanceof SSHConfigManagerDTO){

                sendRequestLog((SSHConfigManagerDTO)messageDTO, session);
            }
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

        closeLog(session);
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

    private void sendRequestLog(SSHConfigManagerDTO configManagerDTO, Session session){

        logger.info("[sendRequestLog] configManagerDTO -> {}", configManagerDTO);

        closeLog(session);

        LogConsumerHandler consumer = this.getCurrentUsers().get(session.getId());

        configManagerDTO.getUserCriteriaDTO().setIdSession(session.getId());

        consumer.getReaderController().startRead(configManagerDTO);
    }

    private void closeLog(Session session){

        logger.info("[closeLog] Cerrando conexion del log.");

        LogConsumerHandler consumer = this.getCurrentUsers().get(session.getId());

        if(consumer.getReaderController() != null){

            consumer.getReaderController().closeRead();
        }
    }
}
