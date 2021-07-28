package local.tin.tests.url.downloader.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 *
 * @author benitodarder
 */
public class StreamUtils {
    

    public static final String ENCODING_DEFLATE = "deflate";
    public static final String ENCODING_GZIP = "gzip";
    public static final String ACCEPTED_ENCODINGS = ENCODING_GZIP + ", " + ENCODING_DEFLATE;
    public static final String ACCEPT_ENCODING_HEADER = "Accept-Encoding";     

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

    public long getChecksumCRC32(InputStream stream, int bufferSize) throws IOException {
        CheckedInputStream checkedInputStream = new CheckedInputStream(stream, new CRC32());
        byte[] buffer = new byte[bufferSize];
        while (checkedInputStream.read(buffer, 0, buffer.length) >= 0) {
        }
        return checkedInputStream.getChecksum().getValue();
    }
}
