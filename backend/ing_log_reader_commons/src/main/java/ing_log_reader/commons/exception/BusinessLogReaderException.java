package ing_log_reader.commons.exception;

public class BusinessLogReaderException extends LogReaderException{

    public BusinessLogReaderException(String message){

        super(message);
    }

    public BusinessLogReaderException(String message, Throwable throwable){

        super(message, throwable);
    }

    public BusinessLogReaderException(Throwable throwable){

        super(throwable);
    }
}
