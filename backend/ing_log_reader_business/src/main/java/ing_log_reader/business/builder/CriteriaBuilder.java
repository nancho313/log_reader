package ing_log_reader.business.builder;

import ing_log_reader.commons.dto.CriteriaDTO;
import ing_log_reader.commons.dto.UserCriteriaDTO;
import ing_log_reader.commons.enums.ResultTypeEnum;
import ing_log_reader.commons.exception.BusinessLogReaderException;
import ing_log_reader.commons.interfaces.ILogCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public enum CriteriaBuilder {

    INSTANCE;

    public String buildCriteria(UserCriteriaDTO userCriteriaDTO, String fileName) throws BusinessLogReaderException{

        CriteriaBuilderConstants.logger.info("[buildCriteria] Inicio buildCriteria, userCriteriaDTO -> {}, fileName -> {}", userCriteriaDTO, fileName);

        String command = null;

        validateUserCriteria(userCriteriaDTO);

        command = String.format(buildCriteriaByResultType(userCriteriaDTO), fileName);

        CriteriaBuilderConstants.logger.info("[buildCriteria] Fin buildCriteria, command -> {}", command);

        return command;
    }

    private String buildCriteriaByResultType(UserCriteriaDTO userCriteriaDTO){

        String command = null;

        switch (userCriteriaDTO.getResultType()){

            case SEARCH:
                command = buildCriteriaSearch(userCriteriaDTO);
                break;

            case TRACE:
                command = buildCriteriaTrace(userCriteriaDTO);
                break;

            case TRACE_SEARCH:
                command = buildCriteriaTraceAndSearch(userCriteriaDTO);
                break;
        }

        return command;
    }

    private String buildCriteriaSearch(UserCriteriaDTO userCriteriaDTO){

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(CriteriaBuilderConstants.GREP);

        stringBuffer.append(appendGrepBody(userCriteriaDTO));

        return stringBuffer.toString();
    }

    private String buildCriteriaTrace(UserCriteriaDTO userCriteriaDTO){

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(CriteriaBuilderConstants.TAIL);

        return stringBuffer.toString();
    }

    private String buildCriteriaTraceAndSearch(UserCriteriaDTO userCriteriaDTO){

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(CriteriaBuilderConstants.TAIL_GREP);

        stringBuffer.append(appendGrepBody(userCriteriaDTO));

        return stringBuffer.toString();
    }

    private String appendGrepBody(UserCriteriaDTO userCriteriaDTO){

        StringBuffer stringBuffer = new StringBuffer();

        for(CriteriaDTO criteria: userCriteriaDTO.getCriterias()){

            stringBuffer.append(criteria.resolveCriteria());
        }

        stringBuffer.append(" -F");

        for(String match: userCriteriaDTO.getMatches()){

            stringBuffer.append(" -e '"+match+"'");
        }

        return stringBuffer.toString();
    }

    private void validateUserCriteria(UserCriteriaDTO userCriteriaDTO) throws BusinessLogReaderException{

        CriteriaBuilderConstants.logger.info("[validateUserCriteria] Inicio validaciones");

        if(userCriteriaDTO == null){

            throw new BusinessLogReaderException("Los criterios ingresados son nulos.");
        }

        List<String> errorMessages = new ArrayList<String>();

        if(userCriteriaDTO.getResultType() == null ){

            errorMessages.add("El tipo de resultado no puede ser nulo.");
        }

        if(userCriteriaDTO.getResultType().equals(ResultTypeEnum.SEARCH) && userCriteriaDTO.getMatches().size() == 0){

            errorMessages.add("El tipo de resultado SEARCH no puede tener matches vacíos.");
        }

        if(errorMessages.size()>0){

            throw new BusinessLogReaderException("Error validando los criterios: "+ errorMessages);
        }

        CriteriaBuilderConstants.logger.info("[validateUserCriteria] Fin validaciones");
    }

    private static class CriteriaBuilderConstants{

        public static final String GREP = "grep %s ";

        public static final String TAIL = "tail -f %s";

        public static final String TAIL_GREP = "tail -f %s | grep";

        public static final Logger logger = LoggerFactory.getLogger(CriteriaBuilder.class);
    }
}
