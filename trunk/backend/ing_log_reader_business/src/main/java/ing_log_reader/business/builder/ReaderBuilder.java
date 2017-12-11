package ing_log_reader.business.builder;

import ing_log_reader.commons.dto.FilterDTO;
import ing_log_reader.commons.dto.ReadDTO;
import ing_log_reader.commons.enums.FilterEnum;

import java.util.List;

public class ReaderBuilder {

    private static final ReaderBuilder INSTANCE = new ReaderBuilder();

    private ReaderBuilder(){

    }

    public ReadDTO getReadDTO(String contentLog){

        return getReadDTO(contentLog, null);
    }

    public ReadDTO getReadDTO(String contentLog, List<FilterDTO> filters){

        ReadDTO readDTO = new ReadDTO();

        String result = contentLog;

        if(filters != null && filters.size() > 0){

            for(FilterDTO filter:filters){

                result = applyFilter(contentLog, filter);
            }

            readDTO.setFilters(filters);
        }

        readDTO.setContentRead(result);

        return readDTO;
    }

    private String applyFilter(String contentLog, FilterDTO filter){

        if(filter.getFilterType().equals(FilterEnum.SEARCH)){

            contentLog = contentLog.replaceAll(filter.getValue(), String.format(filter.getFilterType().getReplacePattern(), filter.getValue().length(), filter.getValue()));
        }

        if(filter.getFilterType().equals(FilterEnum.H_SEARCH)){

            contentLog = contentLog.replaceAll(filter.getValue(), String.format(filter.getFilterType().getReplacePattern(), filter.getValue().length(), filter.getValue()));
        }

        return contentLog;

    }

    public static ReaderBuilder getINSTANCE() {
        return INSTANCE;
    }
}
