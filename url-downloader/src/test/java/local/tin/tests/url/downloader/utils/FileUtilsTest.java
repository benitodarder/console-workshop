package local.tin.tests.url.downloader.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author benitodarder
 */
public class FileUtilsTest {
    
    @Test
    public void isExisting_returns_true_when_file_exists() {
        
        assertTrue(FileUtils.getInstance().isExisting("pom.xml"));
        
    }


    @Test
    public void isExisting_returns_faless_when_file_does_not_exist() {
        
        assertFalse(FileUtils.getInstance().isExisting("pom.xml" + "pom.xml"));
        
    }    
}
