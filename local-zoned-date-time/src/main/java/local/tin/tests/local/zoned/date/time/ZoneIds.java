package local.tin.tests.local.zoned.date.time;

import java.time.ZoneId;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author benitodarder
 */
public class ZoneIds {
    
    private static final Logger LOGGER = Logger.getLogger("ZoneIds");
    
    
    public static void main(String[] args) {
         System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%n");
        LOGGER.info("System included timezones identifiers...");
        for (Entry<String, String> current : ZoneId.SHORT_IDS.entrySet()) {
            Object[] pars = {current.getKey(), current.getValue()};
            LOGGER.log(Level.INFO, "Zone Id. key: {0}, and value: {1}.", pars);
        }
        LOGGER.info("Thats all...");
    }

}
