package local.tin.tests.url.downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import local.tin.tests.url.downloader.utils.StreamUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author benitodarder
 */
public class PlainJava {

    public static final String ENCODING_DEFLATE = "deflate";
    public static final String ENCODING_GZIP = "gzip";
    public static final String ACCEPTED_ENCODINGS = ENCODING_GZIP + ", " + ENCODING_DEFLATE;
    public static final String ACCEPT_ENCODING_HEADER = "Accept-Encoding";
    public static final String USAGE_STRING = "Usage: java -cp url-downloader.jar local.tin.tests.url.downloader.PlainJava <URL>";
    private static final Logger LOGGER = Logger.getLogger(PlainJava.class);

    /**
     * @param args the command line 
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            LOGGER.error(USAGE_STRING);
        } else {
            InputStream inputStream = StreamUtils.getInstance().getInputStream(args[0]);
            String streamAsString = getStreamAsMultiLineString(inputStream);
            LOGGER.info(streamAsString);
        }
    }

    public static String getStreamAsMultiLineString(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining(System.lineSeparator()));
    }

    private static InputStream getInputStream(HttpURLConnection conn) throws IOException {
        String encoding = conn.getContentEncoding();
        InputStream inStr = null;
        if (encoding != null && encoding.equalsIgnoreCase(ENCODING_GZIP)) {
            inStr = new GZIPInputStream(conn.getInputStream());
        } else if (encoding != null && encoding.equalsIgnoreCase(ENCODING_DEFLATE)) {
            inStr = new InflaterInputStream(conn.getInputStream(),
                    new Inflater(true));
        } else {
            inStr = conn.getInputStream();
        }
        return inStr;
    }

}
