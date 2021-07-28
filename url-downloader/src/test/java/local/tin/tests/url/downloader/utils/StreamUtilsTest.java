package local.tin.tests.url.downloader.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author benitodarder
 */
public class StreamUtilsTest {
    
    public static final int BUFFER_SIZE = 512;    
    private static final String SAMPLE_INPUT = "this is a test";
    private static final long SAMPLE_INPUT_CRC = 220129258;
    
    
    @Test
    public void getChecksumCRC32_returns_expected_value() throws IOException {
        
        long result = StreamUtils.getInstance().getChecksumCRC32(new ByteArrayInputStream(SAMPLE_INPUT.getBytes()), BUFFER_SIZE);
        
        assertEquals(SAMPLE_INPUT_CRC, result);
    }
}
