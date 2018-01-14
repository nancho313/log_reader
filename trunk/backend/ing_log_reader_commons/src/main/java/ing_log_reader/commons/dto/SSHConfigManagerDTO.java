package ing_log_reader.commons.dto;

public class SSHConfigManagerDTO extends IConfigManagerDTO {

    public SSHConfigManagerDTO(UserCriteriaDTO userCriteriaDTO){

        setUserCriteriaDTO(userCriteriaDTO);
    }

    public SSHConfigManagerDTO(){

    }

    private String user;

    private String password;

    private String ip;

    private String port;

    private String dirLog;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDirLog() {
        return dirLog;
    }

    public void setDirLog(String dirLog) {
        this.dirLog = dirLog;
    }

    @Override
    public String toString() {
        return "SSHConfigManagerDTO{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", dirLog='" + dirLog + '\'' +
                ", userCriteriaDTO='" + getUserCriteriaDTO() + '\'' +
                '}';
    }
}
