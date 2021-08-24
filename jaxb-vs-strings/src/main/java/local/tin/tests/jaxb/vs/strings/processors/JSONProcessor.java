package local.tin.tests.jaxb.vs.strings.processors;

import java.io.IOException;
import java.util.Date;
import local.tin.tests.jaxb.vs.strings.model.IntegersList;
import local.tin.tests.utils.file.FileUtils;
import local.tin.tests.utils.json.JSONMapper;

/**
 *
 * @author benitodarder
 */
public class JSONProcessor extends Processor {

    public static final String OUTPUT_FILENAME_SUFFIX = "JSONProcessor";    
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(JSONProcessor.class);

    public JSONProcessor(int index, String sampleFileName, String outputPath) {
        super(index, sampleFileName, outputPath);
    }    
    
    @Override
    public void process() {
        try {
            String xmlFile = FileUtils.getInstance().getFileAsString(getSampleFileName() + getOutputFileExtension());
            IntegersList integerList = (IntegersList) JSONMapper.getInstance().getObjectFromJSON(xmlFile, IntegersList.class);
            Integer result = integerList.getIntegers().get(0);
            for (int i = 1; i < integerList.getIntegers().size(); i++) {
                switch (integerList.getOperation()) {
                    case SUM:
                        result = result + integerList.getIntegers().get(i);
                        break;
                    case MULTIPLY:
                        result = result * integerList.getIntegers().get(i);
                        break;
                    case SUBSTRACT:
                        result = result - integerList.getIntegers().get(i);
                        break;
                    case DIVIDE:
                        result = result / integerList.getIntegers().get(i);
                        break;                        
                }

            }
            integerList.setResult(result);
            integerList.setTimestamp(new Date());
            xmlFile = JSONMapper.getInstance().getJSONFromObject(integerList);
            FileUtils.getInstance().saveStringAsFile(getFileName(), xmlFile);
        } catch (IOException ex) {
            LOGGER.error("IOException: " + getSampleFileName() + System.lineSeparator(), ex);
        } 
    }
      

    @Override
    public String getOutputFileExtension() {
        return ".json";
    }    

    @Override
    protected String getFileNameSuffix() {
        return OUTPUT_FILENAME_SUFFIX;
    }
    
    
}
