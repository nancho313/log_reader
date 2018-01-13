package ing_log_reader.commons.enums;

import ing_log_reader.commons.interfaces.ILogCriteria;

public enum CriteriaEnum implements ILogCriteria{

    IGNORE_CASE{

        public String resolveCriteria(){
            return " -i";
        }
    },
    INVERT{

        public String resolveCriteria(){
            return " -v";
        }
    },
    COUNT_MATCHES{

        public String resolveCriteria(){
            return " -c";
        }
    },
    MAX_COUNT{

        public String resolveCriteria(){
            return " -m "+this.getValue();
        }
    },
    LINE_NUMBER{

        public String resolveCriteria(){
            return " -n";
        }
    },
    BEFORE_CONTEXT{

        public String resolveCriteria(){
            return " -B "+this.getValue();
        }
    },
    AFTER_CONTEXT{

        public String resolveCriteria(){
            return " -A "+this.getValue();
        }
    },
    CONTEXT{

        public String resolveCriteria(){
            return " -C "+this.getValue();
        }
    };

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
