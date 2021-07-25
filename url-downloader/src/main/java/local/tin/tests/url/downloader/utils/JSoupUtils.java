package local.tin.tests.url.downloader.utils;

import java.io.IOException;
import java.text.MessageFormat;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author benitodarder
 */
public class JSoupUtils {

    public static final String INLINE_IMG_PREFIX_TEMPLATE = "data:image/{0};charset=utf-8;base64,";
    public static final String URL_ELEMENT_SEPARATOR = "/";
    public static final String FILE_EXTENSION_SEPARATOR = ".";
    public static final String ATRIBUTE_SRC = "src";
    public static final String TAG_IMG = "img";
    
    private JSoupUtils() {
    }

    public static JSoupUtils getInstance() {
        return JSoupUtilsHolder.INSTANCE;
    }

    private static class JSoupUtilsHolder {

        private static final JSoupUtils INSTANCE = new JSoupUtils();
    }

    public void convertToInlineImages(String urlString, Document doc) throws IOException {
        Elements imgs = doc.getElementsByTag(TAG_IMG);
        String urlPrefix = urlString.substring(0, urlString.lastIndexOf(URL_ELEMENT_SEPARATOR));
        for (Element img : imgs) {
            String imgSrc = img.attr(ATRIBUTE_SRC);
            String imageExtension = imgSrc.substring(imgSrc.lastIndexOf(FILE_EXTENSION_SEPARATOR) + 1);
            Object[] parameters = {imageExtension};
            String inlineImagePrefix = MessageFormat.format(INLINE_IMG_PREFIX_TEMPLATE, parameters);
            byte[] imageAsByteArray = StreamUtils.getInstance().getByteArrayFromInputStream(StreamUtils.getInstance().getInputStream(urlPrefix + URL_ELEMENT_SEPARATOR + imgSrc));
            String base64Img = Base64Utils.getInstance().encode(imageAsByteArray);
            img.attr(ATRIBUTE_SRC, inlineImagePrefix + base64Img);
        }
    }

}
