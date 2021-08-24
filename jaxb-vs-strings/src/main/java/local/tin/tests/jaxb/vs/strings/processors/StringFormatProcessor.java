package local.tin.tests.jaxb.vs.strings.processors;

import java.util.Date;
import java.util.List;
import local.tin.tests.jaxb.vs.strings.Utils;
import local.tin.tests.jaxb.vs.strings.model.Operation;

/**
 *
 * @author benitodarder
 */
public class StringFormatProcessor extends RegexExtractorProcessor {

    public static final String OUTPUT_FILENAME_SUFFIX = "StringFormatProcessor";

    public StringFormatProcessor(int index, String sampleFileName, String outputPath) {
        super(index, sampleFileName, outputPath);
    }

    @Override
    protected String getXMLWithSum(List<Integer> integers, int result, Operation operation) {
        String xml = String.format("%s%s", "<IntegersList>", System.lineSeparator());
        xml = String.format("%s%s%s", xml, "<values>", System.lineSeparator());
        for (Integer current : integers) {
            xml = String.format("%s%s%d%s%s", xml, "<integer>", current, "</integer>", System.lineSeparator());
        }
        xml = String.format("%s%s%s", xml, "</values>", System.lineSeparator());
        xml = String.format("%s%s%d%s%s", xml, "<result>", result, "</result>", System.lineSeparator());
        xml = String.format("%s%s%s%s%s", xml, "<operation>", operation.name(), "</operation>", System.lineSeparator());
        xml =  String.format("%s%s%s%s%s", xml, "<timestamp>",  Utils.getInstance().getISO8601Date(new Date()),  "</timestamp>", System.lineSeparator());
        return String.format("%s%s%s", xml, "</IntegersList>", System.lineSeparator());
    }

    @Override
    protected String getFileNameSuffix() {
        return OUTPUT_FILENAME_SUFFIX;
    }
}
