package ing_log_reader.commons.interfaces;

import ing_log_reader.commons.dto.ReadDTO;

import java.util.List;

public interface IReaderPrincipal {

    void sendContentReads(List<ReadDTO> contentReads);
}
