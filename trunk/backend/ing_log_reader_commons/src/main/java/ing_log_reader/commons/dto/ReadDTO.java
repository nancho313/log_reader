package ing_log_reader.commons.dto;

import java.io.Serializable;

public class ReadDTO implements Serializable{

    private String contentRead;

    private FilterDTO filter;

    public String getContentRead() {
        return contentRead;
    }

    public void setContentRead(String contentRead) {
        this.contentRead = contentRead;
    }

    public FilterDTO getFilter() {
        return filter;
    }

    public void setFilter(FilterDTO filter) {
        this.filter = filter;
    }
}
