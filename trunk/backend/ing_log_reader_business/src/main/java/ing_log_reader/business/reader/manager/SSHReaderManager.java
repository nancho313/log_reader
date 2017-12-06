package ing_log_reader.business.reader.manager;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import ing_log_reader.commons.dto.SSHConfigManagerDTO;
import ing_log_reader.commons.exception.BusinessLogReaderException;
import ing_log_reader.commons.exception.LogReaderException;

import java.io.InputStream;
import java.util.Observer;

public class SSHReaderManager extends IReaderManager<SSHConfigManagerDTO> {

    private JSch jSch;

    private Session session;

    private Channel channel;

    public SSHReaderManager(SSHConfigManagerDTO sshConfigManagerDTO, Observer observer){

        super(sshConfigManagerDTO, observer);
    }

    public void read() throws LogReaderException {

        String result = "";

        String commandTail = "tail -f " + getConfigManager().getDirLog();

        try {

            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(commandTail);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            InputStream in = channel.getInputStream();
            channel.connect();

            byte[] tmp = new byte[1024];

            while (!getClosed()) {

                while (in.available() > 0) {

                    int i = in.read(tmp, 0, 1024);

                    if (i < 0) break;

                    result = new String(tmp, 0, i);

                    this.notifyObservers(result);
                }
                if (channel.isClosed()) {

                    break;
                }

                session.sendKeepAliveMsg();

                Thread.sleep(1000);
            }
        } catch (Exception e) {

            throw new BusinessLogReaderException("Hubo un error a la hora de leer el log", e);
        } finally {

            close();
        }
    }

    public void initManager() throws LogReaderException {

        try {

            jSch = new JSch();
            jSch.setConfig("StrictHostKeyChecking", "no");

            String user= getConfigManager().getUser();
            String host= getConfigManager().getIp();
            String password = getConfigManager().getPassword();

            session = jSch.getSession(user, host, 22);
            session.setPassword(password);
            session.connect();

        } catch (Exception e){

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
            read();
        } catch (LogReaderException e) {
            close();
        }
    }
}
