package ing_log_reader.commons.exception;

public class LogReaderException extends Exception{

    public LogReaderException(String message){

        super(message);
    }

    public LogReaderException(String message, Throwable throwable){

        super(message, throwable);
    }

    public LogReaderException(Throwable throwable){

        super(throwable);
    }
}
