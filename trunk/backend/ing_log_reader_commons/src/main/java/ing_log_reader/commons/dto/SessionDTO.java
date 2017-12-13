package ing_log_reader.commons.dto;

import java.io.Serializable;

public class SessionDTO implements Serializable{

    private String idSession;

    private String userName;

    private String logId;

    private FilterDTO filters;

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public FilterDTO getFilters() {
        return filters;
    }

    public void setFilters(FilterDTO filters) {
        this.filters = filters;
    }
}
