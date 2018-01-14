package ing_log_reader.commons.dto;

import java.io.Serializable;

public abstract class IConfigManagerDTO implements Serializable{

    private UserCriteriaDTO userCriteriaDTO;

    public UserCriteriaDTO getUserCriteriaDTO() {
        return userCriteriaDTO;
    }

    public void setUserCriteriaDTO(UserCriteriaDTO userCriteriaDTO) {
        this.userCriteriaDTO = userCriteriaDTO;
    }
}
