package local.tin.tests.jaxb.vs.strings.processors;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import local.tin.tests.jaxb.vs.strings.model.Operation;
import local.tin.tests.utils.file.FileUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author benitodarder
 */
public abstract class RegexExtractorProcessor extends Processor {

    public static final String OPERAND_REGEX = ".*<integer>(\\d+)</integer>.*";
    public static final String OPERATION_REGEX = ".*<operation>(.+)</operation>.*";
    private static final Logger LOGGER = Logger.getLogger(RegexExtractorProcessor.class);

    public RegexExtractorProcessor(int index, String sampleFileName, String outputPath) {
        super(index, sampleFileName, outputPath);
    }


    protected abstract String getXMLWithSum(List<Integer> integers, int result, Operation operation);

    @Override
    protected abstract String getFileNameSuffix();

    @Override
    public void process() {

        BufferedReader bufferedReaderA = null;
        try {
            String operationString = null;
            Pattern patterOperand = Pattern.compile(OPERAND_REGEX, Pattern.DOTALL);
            Pattern patterOperation = Pattern.compile(OPERATION_REGEX, Pattern.DOTALL);
            bufferedReaderA = FileUtils.getInstance().getBufferedReader(getSampleFileName() + getOutputFileExtension());
            String currentLine = bufferedReaderA.readLine();
            List<Integer> integers = new ArrayList<>();
            while (currentLine != null) {
                Matcher operandMatcher = patterOperand.matcher(currentLine);
                Matcher operationMatcher = patterOperation.matcher(currentLine);
                if (operandMatcher.matches()) {
                    int groups = operandMatcher.groupCount();
                    if (groups > 0) {
                        for (int i = 1; i <= groups; i++) {
                            integers.add(Integer.parseInt(operandMatcher.group(i)));
                        }
                    }
                } else if (operationMatcher.matches()) {
                    operationString = operationMatcher.group(1);
                }
                currentLine = bufferedReaderA.readLine();
            }

            int result = integers.get(0);
            for (int i = 1; i < integers.size(); i++) {
                switch (Operation.valueOf(operationString)) {
                    case SUM:
                        result = result + integers.get(i);
                        break;
                    case MULTIPLY:
                        result = result * integers.get(i);
                        break;
                    case SUBSTRACT:
                        result = result - integers.get(i);
                        break;
                    case DIVIDE:
                        result = result / integers.get(i);
                        break;
                }
            }

            FileUtils.getInstance().saveStringAsFile(getFileName(), getXMLWithSum(integers, result, Operation.valueOf(operationString)));

        } catch (FileNotFoundException e) {
            LOGGER.error("FileNotFoundException: " + getSampleFileName() + System.lineSeparator(), e);
        } catch (IOException e) {
            LOGGER.error("IOException: " + getSampleFileName() + System.lineSeparator(), e);
        } finally {
            if (bufferedReaderA != null) {
                try {
                    bufferedReaderA.close();
                } catch (IOException ex) {
                    LOGGER.error("Unexpected IOException closing file: " + getSampleFileName() + System.lineSeparator(), ex);
                }
            }
        }
    }

    @Override
    public String getOutputFileExtension() {
        return ".xml";
    }    
}
