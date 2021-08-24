package local.tin.tests.jaxb.vs.strings.processors;

import java.util.Date;
import java.util.List;
import local.tin.tests.jaxb.vs.strings.Utils;
import local.tin.tests.jaxb.vs.strings.model.Operation;

/**
 *
 * @author benitodarder
 */
public class StringConcatenationProcessor extends RegexExtractorProcessor {

    public static final String OUTPUT_FILENAME_SUFFIX = "StringConcatenationProcessor";

    public StringConcatenationProcessor(int index, String sampleFileName, String outputPath) {
        super(index, sampleFileName, outputPath);
    }
    
    
    @Override
    protected String getXMLWithSum(List<Integer> integers, int result, Operation operation) {
        String xml = "<IntegersList>" + System.lineSeparator();
        xml = xml + "<values>" + System.lineSeparator();
        for (Integer current : integers) {
            xml = xml + "<integer>" + current + "</integer>" + System.lineSeparator();
        }
        xml = xml + "</values>" + System.lineSeparator();
        xml = xml + "<result>"+ result + "</result>" + System.lineSeparator();
        xml = xml + "<operation>"+ operation.name() + "</operation>" + System.lineSeparator();
        xml = xml + "<timestamp>" + Utils.getInstance().getISO8601Date(new Date()) + "</timestamp>" + System.lineSeparator();
        xml = xml + "</IntegersList>"+ System.lineSeparator();
        return xml;
    }

    @Override
    protected String getFileNameSuffix() {
        return OUTPUT_FILENAME_SUFFIX;
    }
}
