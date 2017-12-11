package ing_log_reader.commons.enums;

public enum FilterEnum {

    SEARCH("$$SEARCH(%d)%s"),
    H_SEARCH("$$S_SEARCH(%d)%s");

    private String replacePattern;

    FilterEnum(String replacePattern){

        this.replacePattern = replacePattern;
    }

    public String getReplacePattern() {
        return replacePattern;
    }
}
