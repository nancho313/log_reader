package ing_log_reader.business.reader.manager;

import ing_log_reader.commons.dto.ConfigManagerDTO;
import ing_log_reader.commons.dto.SSHConfigManagerDTO;

public class ReaderManagerFactory {

    private static final ReaderManagerFactory INSTANCE = new ReaderManagerFactory();

    private ReaderManagerFactory(){

    }

    public IReaderManager getReaderManager(ConfigManagerDTO configManagerDTO){

        IReaderManager result = null;

        if(configManagerDTO instanceof SSHConfigManagerDTO){

            result = new SSHReaderManager((SSHConfigManagerDTO)configManagerDTO);
        }

        return result;
    }

    public static ReaderManagerFactory getINSTANCE() {
        return INSTANCE;
    }
}
