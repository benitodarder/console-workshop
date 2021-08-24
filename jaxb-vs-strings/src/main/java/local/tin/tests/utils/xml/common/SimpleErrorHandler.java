package local.tin.tests.utils.xml.common;

import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *  From http://www.edankert.com/validate.html#Error_Handler
 * 
 * @author benito.darder
 */
public class SimpleErrorHandler implements ErrorHandler {

    private final Logger logger;
    
    public SimpleErrorHandler(Logger logger) {
        this.logger = logger;
    }
    
    public void warning(SAXParseException e) throws SAXException {
        logger.warn(e.getLocalizedMessage());
    }

    public void error(SAXParseException e) throws SAXException {
        logger.error(e.getLocalizedMessage());
    }

    public void fatalError(SAXParseException e) throws SAXException {
        logger.fatal(e.getLocalizedMessage());
    }
}
