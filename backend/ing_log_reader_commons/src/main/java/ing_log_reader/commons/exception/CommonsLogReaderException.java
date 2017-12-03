package ing_log_reader.commons.exception;

public class CommonsLogReaderException extends LogReaderException {

    public CommonsLogReaderException(String message){

        super(message);
    }

    public CommonsLogReaderException(String message, Throwable throwable){

        super(message, throwable);
    }

    public CommonsLogReaderException(Throwable throwable){

        super(throwable);
    }
}
