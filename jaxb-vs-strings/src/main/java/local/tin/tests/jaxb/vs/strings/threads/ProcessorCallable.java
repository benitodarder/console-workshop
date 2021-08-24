package local.tin.tests.jaxb.vs.strings.threads;

import java.util.concurrent.Callable;
import local.tin.tests.jaxb.vs.strings.model.ProcessorCallableResult;
import local.tin.tests.jaxb.vs.strings.processors.interfaces.IProcessor;
import org.apache.log4j.Logger;

/**
 *
 * @author benitodarder
 */
public class ProcessorCallable implements Callable {
 
    private static final Logger LOGGER = Logger.getLogger(ProcessorCallable.class);
    private final IProcessor processor;
    
    public ProcessorCallable(IProcessor processor) {
        this.processor = processor;
    }

    @Override
    public ProcessorCallableResult call() throws Exception {
        long t0 = System.currentTimeMillis();
        processor.process();
        long t1 = System.currentTimeMillis() - t0;
        return new ProcessorCallableResult(t1, processor, processor.getClass().getSimpleName() + "," + processor.getIndex() + "," + t1);
    }

}
