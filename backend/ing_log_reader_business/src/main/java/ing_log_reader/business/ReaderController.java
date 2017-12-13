package ing_log_reader.business;

import ing_log_reader.business.builder.ReaderBuilder;
import ing_log_reader.business.reader.manager.IReaderManager;
import ing_log_reader.business.reader.manager.ReaderManagerFactory;
import ing_log_reader.commons.dto.ConfigManagerDTO;
import ing_log_reader.commons.dto.ReadDTO;
import ing_log_reader.commons.exception.BusinessLogReaderException;
import ing_log_reader.commons.interfaces.IReaderPrincipal;

import java.util.Observable;
import java.util.Observer;

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

    public void startRead(ConfigManagerDTO configManagerDTO){

        this.idSession = configManagerDTO.getIdSession();

        this.readerManager = ReaderManagerFactory.getINSTANCE().getReaderManager(this, configManagerDTO);

        this.readerManager.run();
    }

    public void closeRead(){

        this.readerManager.closeManager();
    }

    public void sendContentReads(String contentLog){

        this.getiReaderPrincipal().sendContentReads(ReaderBuilder.getINSTANCE().getReadDTO(contentLog), this.idSession);
    }

    private void applyFilters(String contentLog){


    }

    private IReaderPrincipal getiReaderPrincipal() {
        return iReaderPrincipal;
    }
}