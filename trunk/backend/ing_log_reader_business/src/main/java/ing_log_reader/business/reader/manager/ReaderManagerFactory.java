package ing_log_reader.business.reader.manager;

import ing_log_reader.business.ReaderController;
import ing_log_reader.commons.dto.IConfigManagerDTO;
import ing_log_reader.commons.dto.SSHConfigManagerDTO;

public enum ReaderManagerFactory {

    INSTANCE;

    public IReaderManager getReaderManager(ReaderController readerController, IConfigManagerDTO configManager){

        IReaderManager result = null;

        if(configManager instanceof SSHConfigManagerDTO){

            result = new SSHReaderManager((SSHConfigManagerDTO)configManager, readerController);
        }

        return result;
    }
}
