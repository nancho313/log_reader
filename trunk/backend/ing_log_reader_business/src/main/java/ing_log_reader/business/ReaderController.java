package ing_log_reader.business;

import ing_log_reader.business.builder.ReaderBuilder;
import ing_log_reader.business.reader.manager.IReaderManager;
import ing_log_reader.business.reader.manager.ReaderManagerFactory;
import ing_log_reader.commons.dto.IConfigManagerDTO;

import ing_log_reader.commons.exception.BusinessLogReaderException;
import ing_log_reader.commons.interfaces.IReaderPrincipal;

public class ReaderController {

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

        this.idSession = configManager.getUserSettingsDTO().getIdSession();

        this.readerManager = ReaderManagerFactory.INSTANCE.getReaderManager(this, configManager);

        this.readerManager.run();
    }

    public void closeRead(){

        this.readerManager.closeManager();
    }

    public void sendContentReads(String contentLog){

        this.getiReaderPrincipal().sendContentReads(ReaderBuilder.INSTANCE.getReadDTO(contentLog), this.idSession);
    }

    private void applyFilters(String contentLog){


    }

    private IReaderPrincipal getiReaderPrincipal() {
        return iReaderPrincipal;
    }
}