package ing_log_reader.business.reader.manager;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import ing_log_reader.business.controller.ReaderController;
import ing_log_reader.business.builder.CriteriaBuilder;
import ing_log_reader.commons.dto.SSHConfigManagerDTO;
import ing_log_reader.commons.exception.BusinessLogReaderException;
import ing_log_reader.commons.exception.LogReaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class SSHReaderManager extends IReaderManager<SSHConfigManagerDTO> {

    private JSch jSch;

    private Session session;

    private Channel channel;

    private static int LENGTH_READ = 2048;

    private static int SLEEP_TIME = 250;

    private static final Logger logger = LoggerFactory.getLogger(SSHReaderManager.class);

    public SSHReaderManager(SSHConfigManagerDTO sshConfigManagerDTO, ReaderController readerController){

        super(sshConfigManagerDTO, readerController);
    }

    public void read() throws LogReaderException {

        logger.info("[read] Inicio read ...");

        String result;

        String command = CriteriaBuilder.INSTANCE.buildCriteria(getConfigManager().getUserCriteriaDTO(), getConfigManager().getDirLog());

        logger.info("[read] commandTail -> {} ", command);

        try {

            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            InputStream in = channel.getInputStream();
            channel.connect();

            byte[] tmp = new byte[LENGTH_READ];

            logger.info("[read] Antes de llegar al !getClosed() ...");

            while (!getClosed()) {

                while (in.available() > 0) {

                    int i = in.read(tmp, 0, LENGTH_READ);

                    if (i < 0) break;

                    result = new String(tmp, 0, i);

                    this.getReaderController().sendContentReads(result);

                    if(getClosed()){

                        break;
                    }

                }

                if (channel.isClosed()) {

                    break;
                }

                session.sendKeepAliveMsg();

                Thread.sleep(SLEEP_TIME);
            }
        } catch (Exception e) {

            logger.error("[read] Error al realizar la lectura ", e);

            throw new BusinessLogReaderException("Hubo un error a la hora de leer el log", e);

        } finally {

            close();

            logger.info("[read] cerro la conexion");
        }
    }

    public void initManager() throws LogReaderException {

        try {

            logger.info("[initManager] Inicializando comunicacion SSH ...");

            jSch = new JSch();
            jSch.setConfig("StrictHostKeyChecking", "no");

            String user= getConfigManager().getUser();
            String host= getConfigManager().getIp();
            String password = getConfigManager().getPassword();

            session = jSch.getSession(user, host, 22);
            session.setPassword(password);
            session.connect();

            logger.info("[initManager] Finalizo comunicacion SSH ...");

        } catch (Exception e){

            logger.error("[initManager] Error al inicializar la comunicacion", e);

            throw new BusinessLogReaderException("No se pudo iniciar la conexion.", e);
        }
    }

    public boolean isAvailable() {

        return channel != null &&
               session != null &&
               !channel.isClosed() &&
               session.isConnected();
    }

    void close() {

        if (channel != null) {

            channel.disconnect();
        }

        if (session != null) {

            session.disconnect();
        }

        jSch = null;
    }

    public void run() {

        try {

            initManager();

            read();
        } catch (LogReaderException e) {
            close();
        }
    }
}
