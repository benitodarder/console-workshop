package local.tin.tests.jwt.workshop;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author benitodarder
 */
public class App {

    public static final String USAGE = "Usage: java -cp <Path>/jwt-workshop-1.0-jar-with-dependencies.jar <Class> <Arguments>\n"
            + System.lineSeparator() + "local.tin.tests.jwt.workshop.GetToken";
    private static final Logger LOGGER = LogManager.getLogger(App.class);
    public static final String PROPERTY_SECRET = "secret";
    public static final String PROPERTY_SUBJECT = "subject";
    public static final String PROPERTY_VALID_FOR = "validFor";
    public static final String PROPERTY_ISSUER = "issuer";
    public static final String PROPERTY_ID = "id";

    public static void main(String[] args) throws IOException {
        LOGGER.warn(USAGE);
    }

    protected static Properties getPropertiesFile(String filePath) throws IOException {
        try (InputStream fileInputStream = new FileInputStream(filePath)) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties;
        }

    }
}
