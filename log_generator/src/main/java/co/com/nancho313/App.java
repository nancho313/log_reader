package co.com.nancho313;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
    private static String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";

    public static void main( String[] args )
    {
        Thread thread = new Thread(new Runnable() {

            private Logger logger = LoggerFactory.getLogger("co.com.nancho313");

            public void run() {

                while (true){

                    logger.info(LOREM_IPSUM);

                    logger.info(""+new Date().getTime());

                    try {

                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }
}
