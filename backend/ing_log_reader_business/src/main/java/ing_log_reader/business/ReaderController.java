package ing_log_reader.business;

import ing_log_reader.commons.dto.ReadDTO;
import ing_log_reader.commons.exception.BusinessLogReaderException;
import ing_log_reader.commons.interfaces.IReaderPrincipal;

import java.util.Observable;
import java.util.Observer;

public class ReaderController {

    private IReaderPrincipal iReaderPrincipal;


    public ReaderController(IReaderPrincipal iReaderPrincipal) throws BusinessLogReaderException{

        if (iReaderPrincipal == null){

            throw new BusinessLogReaderException("El reader principal no se encuentra inicializado.");
        }

        this.iReaderPrincipal = iReaderPrincipal;
    }

    public void sendContentReads(String contentLog){


    }

    private void applyFilters(String contentLog){


    }

    private IReaderPrincipal getiReaderPrincipal() {
        return iReaderPrincipal;
    }
}