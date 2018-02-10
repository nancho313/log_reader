package ing_log_reader.commons.dto;

import ing_log_reader.commons.enums.CriteriaEnum;

import java.io.Serializable;

public class CriteriaDTO implements Serializable {

    private CriteriaEnum criteria;

    private int value;

    public String resolveCriteria() {

        criteria.setValue(this.value);

        return criteria.resolveCriteria();
    }

    public CriteriaEnum getCriteria() {
        return criteria;
    }

    public void setCriteria(CriteriaEnum criteria) {
        this.criteria = criteria;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
