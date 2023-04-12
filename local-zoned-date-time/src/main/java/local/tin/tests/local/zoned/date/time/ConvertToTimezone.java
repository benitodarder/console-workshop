package local.tin.tests.local.zoned.date.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author benitodarder
 */
public class ConvertToTimezone {

    private static final String DEFAULT_TIMEZONE = "UTC";
    private static final Logger LOGGER = Logger.getLogger("ConvertToTimezone");

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%n");
        String timeZone = DEFAULT_TIMEZONE;
        if (args.length != 4) {
            LOGGER.info("<yyyy-mm-dd> <hh:mm:ss> <source zone id.> <target zone id.>");
            System.exit(0);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of(args[2]));
        ZonedDateTime zoneDateTime = ZonedDateTime.parse(args[0] + " " + args[1], formatter);
        LOGGER.log(Level.INFO, "Source timestamp: {0}", zoneDateTime.format(DateTimeFormatter.ISO_DATE_TIME));
        LOGGER.log(Level.INFO, "Target timestamp: {0}", zoneDateTime.withZoneSameInstant(ZoneId.of(args[3])).format(DateTimeFormatter.ISO_DATE_TIME));
        LOGGER.info("Thats all...");
    }

}
