package local.tin.tests.url.downloader.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import local.tin.tests.url.downloader.model.JSoupConf;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author benitodarder
 */
public class JSoupUtils {

    public static final String LINK_PROTOCOL = "http";
    public static final String INLINE_ELEMENT_PREFIX = "data:";
    public static final String INLINE_IMG_PREFIX_TEMPLATE = INLINE_ELEMENT_PREFIX + "image/{0};charset=utf-8;base64,";
    public static final String URL_ELEMENT_SEPARATOR = "/";
    public static final String FILE_EXTENSION_SEPARATOR = ".";
    public static final String ATRIBUTE_SRC = "src";
    public static final String TAG_IMG = "img";
    public static final int CRC_BUFFER_SIZE = 512;

    private JSoupUtils() {
    }

    public static JSoupUtils getInstance() {
        return JSoupUtilsHolder.INSTANCE;
    }

    private static class JSoupUtilsHolder {

        private static final JSoupUtils INSTANCE = new JSoupUtils();
    }

    public void convertToInlineImages(JSoupConf jSoupConf) throws IOException {
        Elements imgs = jSoupConf.getDocument().getElementsByTag(TAG_IMG);
        String urlPrefix = jSoupConf.getUrlString().substring(0, jSoupConf.getUrlString().lastIndexOf(URL_ELEMENT_SEPARATOR));
        for (Element img : imgs) {
            String imgSrc = img.attr(ATRIBUTE_SRC);
            if (!imgSrc.startsWith(INLINE_ELEMENT_PREFIX)) {
                String imageExtension = imgSrc.substring(imgSrc.lastIndexOf(FILE_EXTENSION_SEPARATOR) + 1);
                if (!imgSrc.startsWith(LINK_PROTOCOL)) {
                    imgSrc = getLocalImageAsURI(imgSrc, urlPrefix);
                }
                Object[] parameters = {imageExtension};
                String inlineImagePrefix = MessageFormat.format(INLINE_IMG_PREFIX_TEMPLATE, parameters);
                InputStream inputStream = StreamUtils.getInstance().getInputStream(imgSrc);
                byte[] imageAsByteArray = StreamUtils.getInstance().getByteArrayFromInputStream(inputStream);
                String base64Img = Base64Utils.getInstance().encode(imageAsByteArray);
                img.attr(ATRIBUTE_SRC, inlineImagePrefix + base64Img);
            }
        }
    }

    private String getLocalImageAsURI(String imgSrc, String urlPrefix) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(urlPrefix).append(URL_ELEMENT_SEPARATOR).append(imgSrc);
        return stringBuilder.toString();
    }

    public void convertToLocalImages(JSoupConf jSoupConf) throws IOException {
        Elements imgs = jSoupConf.getDocument().getElementsByTag(TAG_IMG);
        String urlPrefix = jSoupConf.getUrlString().substring(0, jSoupConf.getUrlString().lastIndexOf(URL_ELEMENT_SEPARATOR));
        for (Element img : imgs) {
            String imgSrc = img.attr(ATRIBUTE_SRC);
            if (!imgSrc.startsWith(INLINE_ELEMENT_PREFIX)) {
                String imageExtension = imgSrc.substring(imgSrc.lastIndexOf(FILE_EXTENSION_SEPARATOR) + 1);
                if (!imgSrc.startsWith(LINK_PROTOCOL)) {
                    imgSrc = getLocalImageAsURI(imgSrc, urlPrefix);
                }
                InputStream inputStream = StreamUtils.getInstance().getInputStream(imgSrc);
                long currentCRC = StreamUtils.getInstance().getChecksumCRC32(inputStream, CRC_BUFFER_SIZE);
                String localFilePath = getLocalFilePath(jSoupConf, currentCRC, imageExtension);
                if (!FileUtils.getInstance().isExisting(localFilePath)) {
                    inputStream = StreamUtils.getInstance().getInputStream(imgSrc);
                    FileUtils.getInstance().inputStreamToFile(inputStream, localFilePath);
                }
                img.attr(ATRIBUTE_SRC, localFilePath);
            }
        }
    }

    private String getLocalFilePath(JSoupConf jSoupConf, long currentCRC, String fileExtension) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(jSoupConf.getLocalFilesPath())
                .append(System.getProperty("file.separator"))
                .append(currentCRC)
                .append(FILE_EXTENSION_SEPARATOR)
                .append(fileExtension);
        return stringBuilder.toString();
    }
    
   
}
