package local.tin.tests.jaxb.vs.strings.processors;

import java.util.Date;
import java.util.List;
import local.tin.tests.jaxb.vs.strings.Utils;
import local.tin.tests.jaxb.vs.strings.model.Operation;

/**
 *
 * @author benitodarder
 */
public class StringBuilderProcessor extends RegexExtractorProcessor {

    public static final String OUTPUT_FILENAME_SUFFIX = "StringBuilderProcessor";
    
    public StringBuilderProcessor(int index, String sampleFileName, String outputPath) {
        super(index, sampleFileName, outputPath);
    }

    @Override
    protected String getXMLWithSum(List<Integer> integers, int result, Operation operation) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<IntegersList>").append(System.lineSeparator());
        stringBuilder.append("<values>").append(System.lineSeparator());
        for (Integer current : integers) {
            stringBuilder.append("<integer>").append(current).append("</integer>").append(System.lineSeparator());
        }
        stringBuilder.append("</values>").append(System.lineSeparator());
        stringBuilder.append("<result>").append(result).append("</result>").append(System.lineSeparator());
        stringBuilder.append("<operation>").append(operation.name()).append("</operation>").append(System.lineSeparator());
        stringBuilder.append("<timestamp>").append(Utils.getInstance().getISO8601Date(new Date())).append("</timestamp>").append(System.lineSeparator());
        stringBuilder.append("</IntegersList>").append(System.lineSeparator());
        return stringBuilder.toString();
    }

    @Override
    protected String getFileNameSuffix() {
        return OUTPUT_FILENAME_SUFFIX;
    }
}
