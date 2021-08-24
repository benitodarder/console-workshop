package local.tin.tests.model.domain.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author benito.darder
 */
public class CommonException extends Exception {

    public CommonException() {
    }
    
    
    
    public CommonException(Exception e) {
        super(e);
    }

    public CommonException(String string) {
        super(string);
    }

    public CommonException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public CommonException(Throwable thrwbl) {
        super(thrwbl);
    }

    /**
     * Returns exception stack trace as string.
     * 
     * @return String containing the stack trace,
     */
    public String getStackTraceAsString() {
        StringWriter sw = new StringWriter();
        this.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
