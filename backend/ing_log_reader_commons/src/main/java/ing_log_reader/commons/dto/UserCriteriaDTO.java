package ing_log_reader.commons.dto;

import ing_log_reader.commons.enums.CriteriaEnum;
import ing_log_reader.commons.enums.ResultTypeEnum;
import ing_log_reader.commons.interfaces.ILogCriteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserCriteriaDTO implements Serializable {

    private String idSession;

    private ResultTypeEnum resultType;

    private List<String> matches;

    private List<CriteriaDTO> criterias;

    public ResultTypeEnum getResultType() {
        return resultType;
    }

    public void setResultType(ResultTypeEnum resultType) {
        this.resultType = resultType;
    }

    public List<String> getMatches() {

        if(matches == null){
            matches = new ArrayList<String>();
        }

        return matches;
    }

    public void setMatches(List<String> matches) {
        this.matches = matches;
    }

    public List<CriteriaDTO> getCriterias() {

        if(criterias == null){
            criterias = new ArrayList<CriteriaDTO>();
        }

        return criterias;
    }

    public void setCriterias(List<CriteriaDTO> criterias) {
        this.criterias = criterias;
    }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    @Override
    public String toString() {
        return "UserCriteriaDTO{" +
                "idSession='" + idSession + '\'' +
                ", resultType=" + resultType +
                ", matches=" + matches +
                ", criterias=" + criterias +
                '}';
    }
}
