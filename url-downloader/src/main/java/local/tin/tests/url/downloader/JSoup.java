package local.tin.tests.url.downloader;

import java.io.IOException;
import javax.xml.transform.TransformerException;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author benitodarder
 */
public class JSoup {

    public static final String USAGE_STRING = "Usage: java -cp url-downloader.jar local.tin.tests.url.downloader.JSoup <URL>";
    private static final Logger LOGGER = Logger.getLogger(JSoup.class);

    /**
     * @param args the command line
     * @throws javax.xml.transform.TransformerException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws TransformerException, IOException {
        if (args.length != 1) {
            LOGGER.error(USAGE_STRING);
        } else {
            Document doc = (Document) Jsoup.connect(args[0]).get();
            LOGGER.info(doc.html());
            Elements imgs = doc.getElementsByTag("img");
            String urlPrefix = args[0].substring(0, args[0].lastIndexOf("/"));
            for (Element link : imgs) {
                String imgSrc = link.attr("src");
                LOGGER.info(urlPrefix + "/" + imgSrc);
            }
        }
    }

}
