package ing_log_reader.business.reader.manager;

import ing_log_reader.commons.dto.ConfigManagerDTO;
import ing_log_reader.commons.exception.LogReaderException;

import java.util.Observable;
import java.util.Observer;

public abstract class IReaderManager<T extends ConfigManagerDTO> extends Observable implements Runnable{

    private T configManager;

    private Boolean closed = Boolean.FALSE;

    abstract void read() throws LogReaderException;

    abstract void initManager() throws LogReaderException;

    abstract boolean isAvailable();

    abstract void close();

    public IReaderManager(T configManager, Observer observer){

        this.configManager = configManager;

        this.addObserver(observer);
    }

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
}