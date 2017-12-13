package ing_log_reader.api.dto;

import ing_log_reader.business.ReaderController;
import ing_log_reader.commons.exception.BusinessLogReaderException;
import ing_log_reader.commons.interfaces.IReaderPrincipal;

import javax.websocket.Session;
import java.io.Serializable;

public class LogConsumerDTO implements Serializable {

    private Session session;

    private ReaderController readerController;

    public LogConsumerDTO(IReaderPrincipal iReaderPrincipal, Session session){

        this.session = session;
        try {
            this.readerController = new ReaderController(iReaderPrincipal);
        } catch (BusinessLogReaderException e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public ReaderController getReaderController() {
        return readerController;
    }

    public void setReaderController(ReaderController readerController) {
        this.readerController = readerController;
    }
}
