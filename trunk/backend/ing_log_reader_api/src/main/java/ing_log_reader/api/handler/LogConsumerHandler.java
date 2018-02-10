package ing_log_reader.api.handler;

import ing_log_reader.business.controller.ReaderController;
import ing_log_reader.commons.exception.BusinessLogReaderException;
import ing_log_reader.commons.interfaces.IReaderPrincipal;

import javax.websocket.Session;

public class LogConsumerHandler{

    private Session session;

    private ReaderController readerController;

    public LogConsumerHandler(IReaderPrincipal iReaderPrincipal, Session session){

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
