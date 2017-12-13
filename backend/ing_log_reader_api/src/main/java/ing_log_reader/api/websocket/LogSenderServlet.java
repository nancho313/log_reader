package ing_log_reader.api.websocket;

import com.google.gson.Gson;
import ing_log_reader.api.dto.LogConsumerDTO;
import ing_log_reader.commons.dto.ReadDTO;
import ing_log_reader.commons.dto.SSHConfigManagerDTO;

import ing_log_reader.commons.interfaces.IReaderPrincipal;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/LogSender")
public class LogSenderServlet implements IReaderPrincipal {

    private Map<String, LogConsumerDTO> currentUsers;

    private Gson gson = new Gson();

    @OnOpen
    public void onOpen(Session session) {

        this.getCurrentUsers().put(session.getId(), new LogConsumerDTO(this, session));

        //Prueba
        LogConsumerDTO consumer = this.getCurrentUsers().get(session.getId());

        SSHConfigManagerDTO configManagerDTO = new SSHConfigManagerDTO();

        configManagerDTO.setDirLog("/usr/local/jboss-portal-2.7.1/server/default/log/server.log");

        configManagerDTO.setIp("10.69.60.96");

        configManagerDTO.setPassword("consulta_log");

        configManagerDTO.setUser("consulta_log");

        configManagerDTO.setIdSession(session.getId());

        consumer.getReaderController().startRead(configManagerDTO);
    }

    @OnMessage
    public void onMessage(String message, Session session){

        System.out.println("Luego se implementa");

        //session.getBasicRemote().sendText("Parse le estoy respondiendo en el onOpen");
    }

    @Override
    public void sendContentReads(ReadDTO contentReads, String idSession) {

        LogConsumerDTO consumer = this.getCurrentUsers().get(idSession);

        String message = this.getGson().toJson(contentReads);

        try {
            consumer.getSession().getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, LogConsumerDTO> getCurrentUsers() {

        if(currentUsers == null){
            currentUsers = new HashMap<String, LogConsumerDTO>();
        }

        return currentUsers;
    }

    public Gson getGson() {
        return gson;
    }
}
