package local.tin.tests.url.downloader.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import static local.tin.tests.url.downloader.PlainJava.ACCEPTED_ENCODINGS;
import static local.tin.tests.url.downloader.PlainJava.ACCEPT_ENCODING_HEADER;
import static local.tin.tests.url.downloader.PlainJava.ENCODING_DEFLATE;
import static local.tin.tests.url.downloader.PlainJava.ENCODING_GZIP;

/**
 *
 * @author benitodarder
 */
public class StreamUtils {

    private StreamUtils() {
    }

    public static StreamUtils getInstance() {
        return StreamUtilsHolder.INSTANCE;
    }

    private static class StreamUtilsHolder {

        private static final StreamUtils INSTANCE = new StreamUtils();
    }

    public InputStream getInputStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Cast shouldn't fail
        HttpURLConnection.setFollowRedirects(true);
        conn.setRequestProperty(ACCEPT_ENCODING_HEADER, ACCEPTED_ENCODINGS);

        return getInputStream(conn);
    }

    private InputStream getInputStream(HttpURLConnection conn) throws IOException {
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

    public byte[] getByteArrayFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();

    }
}
