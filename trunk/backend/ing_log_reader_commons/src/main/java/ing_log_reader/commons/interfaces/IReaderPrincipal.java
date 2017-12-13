package ing_log_reader.commons.interfaces;

import ing_log_reader.commons.dto.ReadDTO;
import ing_log_reader.commons.dto.SessionDTO;

import java.util.List;

public interface IReaderPrincipal {

    void sendContentReads(ReadDTO contentReads, String idSession);

}
