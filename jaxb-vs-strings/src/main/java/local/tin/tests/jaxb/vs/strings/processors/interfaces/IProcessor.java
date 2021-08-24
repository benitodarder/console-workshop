package local.tin.tests.jaxb.vs.strings.processors.interfaces;

/**
 *
 * @author benitodarder
 */
public interface IProcessor {

    public void process();

    public String getSampleFileName();

    public int getIndex();
    
    public String getOutputFileExtension();
}
