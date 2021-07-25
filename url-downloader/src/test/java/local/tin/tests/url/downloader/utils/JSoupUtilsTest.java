package local.tin.tests.url.downloader.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author benitodarder
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Base64Utils.class, StreamUtils.class})
public class JSoupUtilsTest {

    private static final String URL_STRING_PREFIX = "url string";
    private static final String URL_STRING_SUFIX = "url sufix";
    private static final String ENCODED_BYTE_ARRAY = "encoded";
    private static final String IMG_NAME = "img name";
    private static final String IMG_EXTENSION = "img extension";
    private static final byte[] SAMPLE_BYTE_ARRAY = {2, 4, 8, 16, 32};
    private static Base64Utils mockedBase64Utils;
    private static StreamUtils mockedStreamUtils;
    private InputStream mockedInputStream;
    private Document mockedDocument;
    private Element mockedElement;
    private Elements mockedElements;

    @BeforeClass
    public static void setUpClass() {
        mockedBase64Utils = mock(Base64Utils.class);
        mockedStreamUtils = mock(StreamUtils.class);
    }

    @Before
    public void setUp() throws IOException {
        PowerMockito.mockStatic(Base64Utils.class);
        when(Base64Utils.getInstance()).thenReturn(mockedBase64Utils);
        PowerMockito.mockStatic(StreamUtils.class);
        when(StreamUtils.getInstance()).thenReturn(mockedStreamUtils);
        mockedDocument = mock(Document.class);
        mockedElements = spy(new Elements());
        when(mockedDocument.getElementsByTag(JSoupUtils.TAG_IMG)).thenReturn(mockedElements);
        mockedElement = mock(Element.class);
        mockedElements.add(mockedElement);
        mockedInputStream = mock(InputStream.class);
        when(mockedStreamUtils.getInputStream(URL_STRING_PREFIX + JSoupUtils.URL_ELEMENT_SEPARATOR + IMG_NAME + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION)).thenReturn(mockedInputStream);
        when(mockedStreamUtils.getByteArrayFromInputStream(mockedInputStream)).thenReturn(SAMPLE_BYTE_ARRAY);
        when(mockedBase64Utils.encode(SAMPLE_BYTE_ARRAY)).thenReturn(ENCODED_BYTE_ARRAY);
    }

    @Test
    public void convertToInlinImage_with_images_without_url() throws IOException {
        when(mockedElement.attr(JSoupUtils.ATRIBUTE_SRC)).thenReturn(IMG_NAME + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION);

        JSoupUtils.getInstance().convertToInlineImages(URL_STRING_PREFIX + JSoupUtils.URL_ELEMENT_SEPARATOR + URL_STRING_SUFIX, mockedDocument);

        Object[] argument = {IMG_EXTENSION};
        verify(mockedElement).attr(JSoupUtils.ATRIBUTE_SRC, MessageFormat.format(JSoupUtils.INLINE_IMG_PREFIX_TEMPLATE, argument) + ENCODED_BYTE_ARRAY);
    }

}
