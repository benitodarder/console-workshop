package local.tin.tests.url.downloader;

import java.io.IOException;
import javax.xml.transform.TransformerException;
import local.tin.tests.url.downloader.model.JSoupConf;
import local.tin.tests.url.downloader.utils.JSoupUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author benitodarder
 */
public class JSoup2LocalFiles {

    public static final String USAGE_STRING = "Usage: java -cp url-downloader.jar local.tin.tests.url.downloader.JSoup2LocalFiles <URL> <Local files root path>";
    private static final Logger LOGGER = Logger.getLogger(JSoup2LocalFiles.class);

    /**
     * @param args the command line
     * @throws javax.xml.transform.TransformerException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws TransformerException, IOException {
        if (args.length != 2) {
            LOGGER.error(USAGE_STRING);
        } else {
            Document doc = (Document) Jsoup.connect(args[0]).get();
            JSoupConf jsc = new JSoupConf();
            jsc.setDocument(doc);
            jsc.setUrlString(args[0]);
            jsc.setLocalFilesPath(args[1]);
            JSoupUtils.getInstance().convertToLocalImages(jsc);
            LOGGER.info(doc.html());
        }
    }

}
