package ing_log_reader.business.reader.manager;

import ing_log_reader.business.ReaderController;
import ing_log_reader.commons.dto.IConfigManagerDTO;
import ing_log_reader.commons.exception.LogReaderException;

public abstract class IReaderManager<T extends IConfigManagerDTO> implements Runnable{

    private T configManager;

    private Boolean closed = Boolean.FALSE;

    private ReaderController readerController;

    public IReaderManager(T configManager, ReaderController readerController){

        this.configManager = configManager;

        this.readerController = readerController;
    }

    abstract void read() throws LogReaderException;

    abstract void initManager() throws LogReaderException;

    abstract boolean isAvailable();

    abstract void close();

    public void closeManager(){

        this.closed = Boolean.TRUE;
    }

    public T getConfigManager() {
        return configManager;
    }

    public void setConfigManager(T configManager) {
        this.configManager = configManager;
    }

    public Boolean getClosed() {
        return closed;
    }

    protected ReaderController getReaderController() {
        return readerController;
    }
}
