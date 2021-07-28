package local.tin.tests.url.downloader.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import local.tin.tests.url.downloader.model.JSoupConf;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
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
@PrepareForTest({Base64Utils.class, StreamUtils.class, FileUtils.class})
public class JSoupUtilsTest {

    private static final long SAMPLE_CRC = 666l;    
    private static final String SAMPLE_URL = "https://a.c.d/";
    private static final String URL_STRING_PREFIX = "url string";
    private static final String URL_STRING_SUFIX = "url sufix";
    private static final String ENCODED_BYTE_ARRAY = "encoded";
    private static final String IMG_NAME = "img name";
    private static final String IMG_EXTENSION = "img extension";
    private static final String ROOT_FILE_PATH = "root file path";
    private static final byte[] SAMPLE_BYTE_ARRAY = {2, 4, 8, 16, 32};
    private static Base64Utils mockedBase64Utils;
    private static StreamUtils mockedStreamUtils;
    private static FileUtils mockedFileUtils;
    private InputStream mockedInputStream;
    private Document mockedDocument;
    private Element mockedElement;
    private Elements mockedElements;
    private JSoupConf jSoupConf;

    @BeforeClass
    public static void setUpClass() {
        mockedBase64Utils = mock(Base64Utils.class);
        mockedStreamUtils = mock(StreamUtils.class);
        mockedFileUtils = mock(FileUtils.class);
    }

    @Before
    public void setUp() throws IOException {
        PowerMockito.mockStatic(Base64Utils.class);
        when(Base64Utils.getInstance()).thenReturn(mockedBase64Utils);
        PowerMockito.mockStatic(StreamUtils.class);
        when(StreamUtils.getInstance()).thenReturn(mockedStreamUtils);
        PowerMockito.mockStatic(FileUtils.class);
        when(FileUtils.getInstance()).thenReturn(mockedFileUtils);     
        reset(mockedBase64Utils);
        reset(mockedStreamUtils);
        reset(mockedFileUtils);        
        mockedDocument = mock(Document.class);
        mockedElements = spy(new Elements());
        when(mockedDocument.getElementsByTag(JSoupUtils.TAG_IMG)).thenReturn(mockedElements);
        mockedElement = mock(Element.class);
        mockedElements.add(mockedElement);
        jSoupConf = new JSoupConf();
        jSoupConf.setUrlString(URL_STRING_PREFIX + JSoupUtils.URL_ELEMENT_SEPARATOR + URL_STRING_SUFIX);
        jSoupConf.setDocument(mockedDocument);
    }

    @Test
    public void convertToInlinImage_with_images_without_url() throws IOException {
        when(mockedElement.attr(JSoupUtils.ATRIBUTE_SRC)).thenReturn(IMG_NAME + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION);
        mockedInputStream = mock(InputStream.class);
        when(mockedStreamUtils.getInputStream(URL_STRING_PREFIX + JSoupUtils.URL_ELEMENT_SEPARATOR + IMG_NAME + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION)).thenReturn(mockedInputStream);
        when(mockedStreamUtils.getByteArrayFromInputStream(mockedInputStream)).thenReturn(SAMPLE_BYTE_ARRAY);
        when(mockedBase64Utils.encode(SAMPLE_BYTE_ARRAY)).thenReturn(ENCODED_BYTE_ARRAY);

        JSoupUtils.getInstance().convertToInlineImages(jSoupConf);

        Object[] argument = {IMG_EXTENSION};
        verify(mockedElement).attr(JSoupUtils.ATRIBUTE_SRC, MessageFormat.format(JSoupUtils.INLINE_IMG_PREFIX_TEMPLATE, argument) + ENCODED_BYTE_ARRAY);
    }

    @Test
    public void convertToInlinImage_ignores_already_embedded_image() throws IOException {
        Object[] argument = {IMG_EXTENSION};
        when(mockedElement.attr(JSoupUtils.ATRIBUTE_SRC)).thenReturn(MessageFormat.format(JSoupUtils.INLINE_IMG_PREFIX_TEMPLATE, argument) + ENCODED_BYTE_ARRAY);

        JSoupUtils.getInstance().convertToInlineImages(jSoupConf);

        verify(mockedStreamUtils, never()).getInputStream(anyString());
        verify(mockedStreamUtils, never()).getByteArrayFromInputStream(any(InputStream.class));
        verify(mockedBase64Utils, never()).encode(any(byte[].class));
        verify(mockedElement, never()).attr(eq(JSoupUtils.ATRIBUTE_SRC), anyString());
    }

    @Test
    public void convertToInlinImage_with_images_with_url() throws IOException {
        when(mockedElement.attr(JSoupUtils.ATRIBUTE_SRC)).thenReturn(SAMPLE_URL + IMG_NAME + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION);
        mockedInputStream = mock(InputStream.class);
        when(mockedStreamUtils.getInputStream(SAMPLE_URL + IMG_NAME + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION)).thenReturn(mockedInputStream);
        when(mockedStreamUtils.getByteArrayFromInputStream(mockedInputStream)).thenReturn(SAMPLE_BYTE_ARRAY);
        when(mockedBase64Utils.encode(SAMPLE_BYTE_ARRAY)).thenReturn(ENCODED_BYTE_ARRAY);

        JSoupUtils.getInstance().convertToInlineImages(jSoupConf);

        Object[] argument = {IMG_EXTENSION};
        verify(mockedElement).attr(JSoupUtils.ATRIBUTE_SRC, MessageFormat.format(JSoupUtils.INLINE_IMG_PREFIX_TEMPLATE, argument) + ENCODED_BYTE_ARRAY);
    }
    
    @Test
    public void convertToLocalImages_generates_crc_from_image() throws IOException {
        when(mockedElement.attr(JSoupUtils.ATRIBUTE_SRC)).thenReturn(IMG_NAME + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION);
        mockedInputStream = mock(InputStream.class);
        when(mockedStreamUtils.getInputStream(URL_STRING_PREFIX + JSoupUtils.URL_ELEMENT_SEPARATOR + IMG_NAME + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION)).thenReturn(mockedInputStream);

        JSoupUtils.getInstance().convertToLocalImages(jSoupConf);

        verify(mockedStreamUtils).getChecksumCRC32(mockedInputStream, JSoupUtils.CRC_BUFFER_SIZE);
    }    
    
    @Test
    public void convertToLocalImages_saves_input_stream_when_crc_not_present() throws IOException {
        when(mockedElement.attr(JSoupUtils.ATRIBUTE_SRC)).thenReturn(IMG_NAME + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION);
        mockedInputStream = mock(InputStream.class);
        when(mockedStreamUtils.getInputStream(URL_STRING_PREFIX + JSoupUtils.URL_ELEMENT_SEPARATOR + IMG_NAME + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION)).thenReturn(mockedInputStream);
        jSoupConf.setLocalFilesPath(ROOT_FILE_PATH);
        when(mockedStreamUtils.getChecksumCRC32(mockedInputStream, JSoupUtils.CRC_BUFFER_SIZE)).thenReturn(SAMPLE_CRC);
        when(mockedFileUtils.isExisting(ROOT_FILE_PATH + System.getProperty("file.separator") + SAMPLE_CRC + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION)).thenReturn(false);
        
        JSoupUtils.getInstance().convertToLocalImages(jSoupConf);

        verify(mockedFileUtils).inputStreamToFile(mockedInputStream, ROOT_FILE_PATH + System.getProperty("file.separator") + SAMPLE_CRC + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION);
    }   
    
    @Test
    public void convertToLocalImages_does_not_save_input_stream_when_crc_present() throws IOException {
        when(mockedElement.attr(JSoupUtils.ATRIBUTE_SRC)).thenReturn(IMG_NAME + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION);
        mockedInputStream = mock(InputStream.class);
        when(mockedStreamUtils.getInputStream(URL_STRING_PREFIX + JSoupUtils.URL_ELEMENT_SEPARATOR + IMG_NAME + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION)).thenReturn(mockedInputStream);
        jSoupConf.setLocalFilesPath(ROOT_FILE_PATH);
        when(mockedStreamUtils.getChecksumCRC32(mockedInputStream, JSoupUtils.CRC_BUFFER_SIZE)).thenReturn(SAMPLE_CRC);
        when(mockedFileUtils.isExisting(ROOT_FILE_PATH + System.getProperty("file.separator") + SAMPLE_CRC + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION)).thenReturn(true);
        
        JSoupUtils.getInstance().convertToLocalImages(jSoupConf);

        verify(mockedFileUtils, never()).inputStreamToFile(mockedInputStream, ROOT_FILE_PATH + System.getProperty("file.separator") + SAMPLE_CRC + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION);
    }    
    
    @Test
    public void convertToLocalImages_updates_img_src_to_local_file() throws IOException {
        when(mockedElement.attr(JSoupUtils.ATRIBUTE_SRC)).thenReturn(IMG_NAME + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION);
        mockedInputStream = mock(InputStream.class);
        when(mockedStreamUtils.getInputStream(URL_STRING_PREFIX + JSoupUtils.URL_ELEMENT_SEPARATOR + IMG_NAME + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION)).thenReturn(mockedInputStream);
        jSoupConf.setLocalFilesPath(ROOT_FILE_PATH);
        when(mockedStreamUtils.getChecksumCRC32(mockedInputStream, JSoupUtils.CRC_BUFFER_SIZE)).thenReturn(SAMPLE_CRC);
        when(mockedFileUtils.isExisting(ROOT_FILE_PATH + System.getProperty("file.separator") + SAMPLE_CRC + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION)).thenReturn(false);
        
        JSoupUtils.getInstance().convertToLocalImages(jSoupConf);

        verify(mockedElement).attr(JSoupUtils.ATRIBUTE_SRC, ROOT_FILE_PATH + System.getProperty("file.separator") + SAMPLE_CRC + JSoupUtils.FILE_EXTENSION_SEPARATOR + IMG_EXTENSION);
    }     
    
    @Test
    public void convertToLocalImages_skips_crc_checks_for_inline_images() throws IOException {
        Object[] argument = {IMG_EXTENSION};
        when(mockedElement.attr(JSoupUtils.ATRIBUTE_SRC)).thenReturn(MessageFormat.format(JSoupUtils.INLINE_IMG_PREFIX_TEMPLATE, argument) + ENCODED_BYTE_ARRAY);        mockedInputStream = mock(InputStream.class);
        
        JSoupUtils.getInstance().convertToLocalImages(jSoupConf);

        verify(mockedStreamUtils,never()).getChecksumCRC32(any(InputStream.class), eq(JSoupUtils.CRC_BUFFER_SIZE));
        verify(mockedFileUtils, never()).isExisting(anyString());
        verify(mockedFileUtils, never()).inputStreamToFile(any(InputStream.class), anyString());
    }    
}
