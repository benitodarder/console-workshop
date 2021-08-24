package local.tin.tests.jaxb.vs.strings;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author benitodarder
 */
public class Utils {

    public static final String TIMESTAMP_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    private Utils() {
    }

    public static Utils getInstance() {
        return UtilsHolder.INSTANCE;
    }

    private static class UtilsHolder {

        private static final Utils INSTANCE = new Utils();
    }

    public String getISO8601Date(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_ISO_8601);
        return sdf.format(date);
    }
}
