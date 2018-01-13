package ing_log_reader.commons.dto;

public abstract class IConfigManagerDTO {

    private UserSettingsDTO userSettingsDTO;

    public IConfigManagerDTO(UserSettingsDTO userSettingsDTO){

        this.userSettingsDTO = userSettingsDTO;
    }

    public UserSettingsDTO getUserSettingsDTO() {
        return userSettingsDTO;
    }

    public void setUserSettingsDTO(UserSettingsDTO userSettingsDTO) {
        this.userSettingsDTO = userSettingsDTO;
    }
}
