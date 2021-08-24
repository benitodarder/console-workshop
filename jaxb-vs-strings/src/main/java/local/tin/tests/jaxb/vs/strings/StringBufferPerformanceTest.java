package local.tin.tests.jaxb.vs.strings;

import local.tin.tests.jaxb.vs.strings.processors.StringBufferProcessor;
import org.apache.log4j.Logger;

/**
 *
 * @author benitodarder
 */
public class StringBufferPerformanceTest {

    private static final Logger LOGGER = Logger.getLogger(StringBufferPerformanceTest.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if (args.length == 3) {
            long t0 = System.currentTimeMillis();
            for (int index = 0; index < Integer.parseInt(args[1]); index++) {
                StringBufferProcessor processor = new StringBufferProcessor(index, args[0], args[2]);
                processor.process();
            }
            LOGGER.info("Open " + args[0] + " file, sum all integers extracted with regex, append the result, and save the file using StringBuffer, repeated " + args[1] + " times, took " + (System.currentTimeMillis() - t0) + "ms");            
            LOGGER.info("That's all folks!...");
        } else {
            LOGGER.info("Usage: java -jar StringBufferPerformanceTest <file> <iterations>");
            LOGGER.info("You can save to file redirecting the output:\njava -jar StringBufferPerformanceTest <file> <iterations> > <destination>");
        }

    }

}
