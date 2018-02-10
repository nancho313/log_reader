package ing_log_reader.business;

import ing_log_reader.business.builder.ReaderBuilder;
import ing_log_reader.business.reader.manager.IReaderManager;

import ing_log_reader.business.reader.manager.SSHReaderManager;
import ing_log_reader.commons.dto.SSHConfigManagerDTO;
import ing_log_reader.commons.exception.BusinessLogReaderException;
import ing_log_reader.commons.interfaces.IReaderPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReaderController {

    private static final Logger logger = LoggerFactory.getLogger(ReaderController.class);

    private IReaderPrincipal iReaderPrincipal;

    private IReaderManager readerManager;

    private SSHConfigManagerDTO configManager;

    private String idSession;

    public ReaderController(IReaderPrincipal iReaderPrincipal) throws BusinessLogReaderException{

        if (iReaderPrincipal == null){

            throw new BusinessLogReaderException("El reader principal no se encuentra inicializado.");
        }

        this.iReaderPrincipal = iReaderPrincipal;
    }

    public void startRead(SSHConfigManagerDTO configManager){

        logger.info("[startRead] IConfigManagerDTO : {}", configManager);

        this.configManager = configManager;

        this.idSession = configManager.getUserCriteriaDTO().getIdSession();

        this.readerManager = new SSHReaderManager(configManager, this);

        this.readerManager.start();

        logger.info("[startRead] Se inicio el readerManager correctamente");
    }

    public void closeRead(){

        if(this.readerManager != null){

            logger.info("[closeRead] cerrando readerManager...");

            this.readerManager.closeManager();
        }
    }

    public void sendContentReads(String contentLog){

        contentLog = applyFilters(contentLog);

        this.getiReaderPrincipal().sendContentReads(ReaderBuilder.INSTANCE.getReadDTO(contentLog), this.idSession);
    }

    private String applyFilters(String contentLog){

        contentLog = contentLog.replace("\n", "<br>").replace("\\n", "<br>");

        if(configManager.getUserCriteriaDTO().getMatches().size() > 0){

            for(String match: configManager.getUserCriteriaDTO().getMatches()){

                contentLog = contentLog.replace(match, "<div class='log_match'>"+match+"</div>");
            }
        }

        return contentLog;
    }

    private IReaderPrincipal getiReaderPrincipal() {
        return iReaderPrincipal;
    }
}