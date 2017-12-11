package ing_log_reader.commons.dto;

import java.io.Serializable;
import java.util.List;

public class ReadDTO implements Serializable{

    private String contentRead;

    private List<FilterDTO> filters;

    public String getContentRead() {
        return contentRead;
    }

    public void setContentRead(String contentRead) {
        this.contentRead = contentRead;
    }

    public List<FilterDTO> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterDTO> filters) {
        this.filters = filters;
    }
}
