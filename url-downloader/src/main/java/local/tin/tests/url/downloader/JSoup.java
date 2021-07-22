package local.tin.tests.url.downloader;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.Node;

/**
 *
 * @author benitodarder
 */
public class JSoup {

    public static final String USAGE_STRING = "Usage: java -cp url-downloader.jar local.tin.tests.url.downloader.JSoup <URL>";
    private static final Logger LOGGER = Logger.getLogger(JSoup.class);

    /**
     * @param args the command line argumentsdeflate";
     * @throws javax.xml.transform.TransformerException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws TransformerException, IOException {
        if (args.length != 1) {
            LOGGER.error(USAGE_STRING);
        } else {
            Document doc = (Document) Jsoup.connect(args[0]).get();
            LOGGER.info(doc.html());
        }
    }


}
