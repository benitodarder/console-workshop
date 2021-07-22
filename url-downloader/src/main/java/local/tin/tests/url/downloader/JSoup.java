package local.tin.tests.url.downloader;

import java.io.IOException;
import java.text.MessageFormat;
import javax.xml.transform.TransformerException;
import local.tin.tests.url.downloader.utils.Base64Utils;
import local.tin.tests.url.downloader.utils.StreamUtils;
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
    private static final String INLINE_IMG_PREFIX_TEMPLATE = "data:image/{0};charset=utf-8;base64,";
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
            Elements imgs = doc.getElementsByTag("img");
            String urlPrefix = args[0].substring(0, args[0].lastIndexOf("/"));
            for (Element img : imgs) {
                String imgSrc = img.attr("src");
                String imageExtension = imgSrc.substring(imgSrc.lastIndexOf(".") + 1);
                Object[] parameters = {imageExtension};
                String inlineImagePrefix = MessageFormat.format(INLINE_IMG_PREFIX_TEMPLATE, parameters);
                byte[] imageAsByteArray = StreamUtils.getInstance().getByteArrayFromInputStream(StreamUtils.getInstance().getInputStream(urlPrefix + "/" + imgSrc));
                String base64Img = Base64Utils.getInstance().encode(imageAsByteArray);
                img.attr("src", inlineImagePrefix + base64Img);
            }
            LOGGER.info(doc.html());
        }
    }

}
