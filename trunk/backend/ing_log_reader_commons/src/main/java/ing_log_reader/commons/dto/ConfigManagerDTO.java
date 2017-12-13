package ing_log_reader.commons.dto;

import java.io.Serializable;

public class ConfigManagerDTO implements Serializable {

    private String idSession;

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }
}
