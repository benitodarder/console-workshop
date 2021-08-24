package local.tin.tests.jaxb.vs.strings.processors;

import java.io.File;
import local.tin.tests.jaxb.vs.strings.processors.interfaces.IProcessor;

/**
 *
 * @author benitodarder
 */
public abstract class Processor implements IProcessor {

    private final int index;
    private final String sampleFileName;
    private final String outputPath;

    public Processor(int index, String sampleFileName, String outputPath) {
        this.index = index;
        this.sampleFileName = sampleFileName;
        this.outputPath = outputPath;
    }
    
    
    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getSampleFileName() {
        return sampleFileName;
    }

    protected abstract String getFileNameSuffix();

    protected String getFileName() {
        StringBuilder filePath = new StringBuilder();
        if (outputPath != null && !outputPath.isEmpty()) {
            filePath.append(outputPath);
            if (!File.separator.equals(outputPath.substring(outputPath.length() - 1))) {
                filePath.append(File.separator);
            }
            
        }
        filePath.append(getSampleFileName()).append("_").append(String.format("%05d", index)).append("_").append(getFileNameSuffix()).append(getOutputFileExtension());
        return filePath.toString();
    }

}
