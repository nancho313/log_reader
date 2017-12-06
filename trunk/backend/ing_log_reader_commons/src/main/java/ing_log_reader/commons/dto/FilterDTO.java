package ing_log_reader.commons.dto;

import ing_log_reader.commons.enums.FilterEnum;

import java.io.Serializable;

public class FilterDTO implements Serializable{

    private FilterEnum filterType;

    private String value;

    public FilterEnum getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterEnum filterType) {
        this.filterType = filterType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
