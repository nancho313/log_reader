package ing_log_reader.commons.dto;

public abstract class IConfigManagerDTO {

    private UserCriteriaDTO userCriteriaDTO;

    public IConfigManagerDTO(UserCriteriaDTO userCriteriaDTO){

        this.userCriteriaDTO = userCriteriaDTO;
    }

    public UserCriteriaDTO getUserCriteriaDTO() {
        return userCriteriaDTO;
    }

    public void setUserCriteriaDTO(UserCriteriaDTO userCriteriaDTO) {
        this.userCriteriaDTO = userCriteriaDTO;
    }
}
