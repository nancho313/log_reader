package ing_log_reader.commons.enums;

public enum ResultTypeEnum {

    TRACE(Boolean.FALSE),
    SEARCH(Boolean.TRUE),
    TRACE_SEARCH(Boolean.FALSE);

    private String value;

    private boolean closeable;

    ResultTypeEnum(boolean closeable){

        this.closeable = closeable;
    }

    public boolean isCloseable() {
        return closeable;
    }
}
