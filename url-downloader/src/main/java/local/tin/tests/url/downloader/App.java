package local.tin.tests.url.downloader;

import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *
 * @author benitodarder
 */
public class App {
    
    public static final String USAGE_STRING = "Usage: java -cp url-downloader.jar local.tin.tests.url.downloader.<Downloader class> <URL>\n\t- PlainJava\n\t- JSoup";    
    private static final Logger LOGGER = Logger.getLogger(App.class);
    /**
     * @param args the command line argumentsdeflate";
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
            LOGGER.error(USAGE_STRING);
    }



}
