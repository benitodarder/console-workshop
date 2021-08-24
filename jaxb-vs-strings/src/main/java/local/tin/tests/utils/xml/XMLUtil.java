package local.tin.tests.utils.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import local.tin.tests.utils.xml.common.SimpleErrorHandler;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

/**
 *
 * @author benito.darder
 */
public class XMLUtil {

    public static final String WELL_FORMED_XML = "WELL FORMED XML";
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(XMLUtil.class);
    private static SAXParserFactory saxParserFactory;
    private static DocumentBuilderFactory documentBuilderFactory;
    private static TransformerFactory transformerFactory;

    private XMLUtil() {
    }

    public static XMLUtil getInstance() {
        return XMLUtilHolder.INSTANCE;
    }

    private static class XMLUtilHolder {

        private static final XMLUtil INSTANCE = new XMLUtil();
    }

    /**
     * Returns a org.w3d.dom Document containing the XML document included in
     * the file pointed by filePath
     *
     * @param filePath String
     * @return org.w3d.dom.Document
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public Document getDocumentFromFile(String filePath) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder db = getDocumentBuilderFactory().newDocumentBuilder();
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        return db.parse(fileInputStream);
    }

    /**
     * Returns a pretty printed String containing the org.w3d.dom.Document
     * specified as parameter.
     *
     * @param xml org.w3d.dom.Document
     * @return String
     * @throws TransformerConfigurationException
     * @throws TransformerException
     */
    public String getDocumentPrettyPrinted(Document xml) throws TransformerException {
        Transformer transformer = getTransformerFactory().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
        Writer out = new StringWriter();
        transformer.transform(new DOMSource(xml), new StreamResult(out));
        return out.toString();
    }

    /**
     * Checks if the XML document contained in xmlString is well formed, in that
     * case returns XMLUtil.WELL_FORMED_XML, otherwise the exception's localized
     * message.
     *
     * @param xmlString String
     * @return String
     */
    public String getWellFormedStatusFromXMLReader(String xmlString) {
        String result = WELL_FORMED_XML;
        try {
            SAXParser parser = getSAXParserFactory().newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setErrorHandler(new SimpleErrorHandler(LOGGER));
            reader.parse(new InputSource(new StringReader(xmlString)));
        } catch (SAXException | ParserConfigurationException | IOException ex) {
            result = ex.getLocalizedMessage();
        }
        return result;
    }

    private SAXParserFactory getSAXParserFactory() throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
        synchronized (this) {
            if (saxParserFactory == null) {
                saxParserFactory = SAXParserFactory.newInstance();
                saxParserFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                saxParserFactory.setValidating(false);
                saxParserFactory.setNamespaceAware(true);
            }
        }
        return saxParserFactory;
    }

    private DocumentBuilderFactory getDocumentBuilderFactory() throws ParserConfigurationException {
        synchronized (this) {
            if (documentBuilderFactory == null) {
                documentBuilderFactory = DocumentBuilderFactory.newInstance();
                documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                documentBuilderFactory.setValidating(false);
            }
        }
        return documentBuilderFactory;
    }

    private TransformerFactory getTransformerFactory() throws TransformerConfigurationException {
        synchronized (this) {
            if (transformerFactory == null) {
                transformerFactory = TransformerFactory.newInstance();
                transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            }
        }
        return transformerFactory;
    }

}
