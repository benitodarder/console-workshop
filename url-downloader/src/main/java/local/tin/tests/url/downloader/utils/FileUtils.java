package local.tin.tests.url.downloader.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author benitodarder
 */
public class FileUtils {

    public static final int DEFAULT_BUFFER_SIZE = 8192;
    
    private FileUtils() {
    }

    public static FileUtils getInstance() {
        return FileUtilsHolder.INSTANCE;
    }

    private static class FileUtilsHolder {

        private static final FileUtils INSTANCE = new FileUtils();
    }

    public boolean isExisting(String path) {
        File tempFile = new File(path);
        return tempFile.exists();
    }

    public void inputStreamToFile(InputStream inputStream, String filePath) throws IOException {
        OutputStream target = new FileOutputStream(filePath);
        byte[] buf = new byte[8192];
        int length;
        while ((length = inputStream.read(buf)) > 0) {
            target.write(buf, 0, length);
        }
    }
}
