package ing_log_reader.business;

import ing_log_reader.business.builder.ReaderBuilder;
import ing_log_reader.business.reader.manager.IReaderManager;
import ing_log_reader.business.reader.manager.ReaderManagerFactory;
import ing_log_reader.commons.dto.IConfigManagerDTO;

import ing_log_reader.commons.exception.BusinessLogReaderException;
import ing_log_reader.commons.interfaces.IReaderPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReaderController {

    private static final Logger logger = LoggerFactory.getLogger(ReaderController.class);

    private IReaderPrincipal iReaderPrincipal;

    private IReaderManager readerManager;

    private String idSession;

    public ReaderController(IReaderPrincipal iReaderPrincipal) throws BusinessLogReaderException{

        if (iReaderPrincipal == null){

            throw new BusinessLogReaderException("El reader principal no se encuentra inicializado.");
        }

        this.iReaderPrincipal = iReaderPrincipal;
    }

    public void startRead(IConfigManagerDTO configManager){

        logger.info("[startRead] IConfigManagerDTO : {}", configManager);

        this.idSession = configManager.getUserCriteriaDTO().getIdSession();

        this.readerManager = ReaderManagerFactory.INSTANCE.getReaderManager(this, configManager);

        this.readerManager.run();
    }

    public void closeRead(){

        logger.info("[closeRead] cerrando readerManager...");

        if(this.readerManager != null){

            this.readerManager.closeManager();
        }
    }

    public void sendContentReads(String contentLog){

        //contentLog = applyFilters(contentLog);

        this.getiReaderPrincipal().sendContentReads(ReaderBuilder.INSTANCE.getReadDTO(contentLog), this.idSession);
    }

    private String applyFilters(String contentLog){

        contentLog = contentLog.replace("\n", "<br>");

        return contentLog;
    }

    private IReaderPrincipal getiReaderPrincipal() {
        return iReaderPrincipal;
    }
}