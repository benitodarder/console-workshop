package local.tin.tests.local.zoned.date.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author benitodarder
 */
public class LocalDateTimeToUTC {

    private static final String DEFAULT_TIMEZONE = "UTC";
    private static final Logger LOGGER = Logger.getLogger("LocalDateTimeToUTC");

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%n");
        String timeZone = DEFAULT_TIMEZONE;
        if (args.length == 1) {
            timeZone = args[0];
        }
        LocalDateTime now = LocalDateTime.now();
        ZoneId systemZoneId = ZoneId.systemDefault();
        LOGGER.log(Level.INFO, "Using LocalDateTime, now its: {0}", now.format(DateTimeFormatter.ISO_DATE_TIME));
        LOGGER.log(Level.INFO, "Using ZonedDateTime  with system time zone, now its: {0}", now.atZone(systemZoneId).format(DateTimeFormatter.ISO_DATE_TIME));
        LOGGER.log(Level.INFO, "Converting to timezone: {0}", now.atZone(systemZoneId).withZoneSameInstant(ZoneId.of(timeZone)));                
        LOGGER.info("Thats all...");
    }

}
