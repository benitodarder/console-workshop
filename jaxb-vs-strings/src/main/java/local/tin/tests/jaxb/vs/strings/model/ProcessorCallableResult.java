package local.tin.tests.jaxb.vs.strings.model;

import local.tin.tests.jaxb.vs.strings.processors.interfaces.IProcessor;

/**
 *
 * @author benitodarder
 */
public class ProcessorCallableResult {
    
    private final long time;
    private final IProcessor processor;
    private final String message;

    public ProcessorCallableResult(long time, IProcessor processor, String message) {
        this.time = time;
        this.processor = processor;
        this.message = message;
    }



    public long getTime() {
        return time;
    }

    public IProcessor getProcessor() {
        return processor;
    }

    public String getMessage() {
        return message;
    }
    
    
}
