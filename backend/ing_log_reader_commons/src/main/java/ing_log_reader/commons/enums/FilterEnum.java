package ing_log_reader.commons.enums;

public enum FilterEnum {

    SEARCH("$$SEARCH(%d)%s"),
    HIGHLIGHT_SEARCH("$$HIGHLIGHT_SEARCH(%d)%s");

    private String replacePattern;

    FilterEnum(String replacePattern){

        this.replacePattern = replacePattern;
    }

    public String getReplacePattern() {
        return replacePattern;
    }
}
