package local.tin.tests.jaxb.vs.strings;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import local.tin.tests.jaxb.vs.strings.model.ProcessorCallableResult;
import local.tin.tests.jaxb.vs.strings.processors.JAXBProcessor;
import local.tin.tests.jaxb.vs.strings.processors.JSONProcessor;
import local.tin.tests.jaxb.vs.strings.processors.StringBufferProcessor;
import local.tin.tests.jaxb.vs.strings.processors.StringBuilderProcessor;
import local.tin.tests.jaxb.vs.strings.processors.StringConcatenationProcessor;
import local.tin.tests.jaxb.vs.strings.processors.StringFormatProcessor;
import local.tin.tests.jaxb.vs.strings.threads.ProcessorCallable;
import local.tin.tests.utils.file.FileUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author benitodarder
 */
public class ExecutorCompletionPerformanceTest {

    private static final Logger LOGGER = Logger.getLogger(ExecutorCompletionPerformanceTest.class);
    private static final int THREADPOOL_SIZE = 5;

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     * @throws java.util.concurrent.ExecutionException
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        if (args.length == 3) {
            long t0 = System.currentTimeMillis();
            long stringConcatenationProcessor = 0;
            long stringBuilderProcessor = 0;
            long jaxbProcessor = 0;
            long jsonProcessor = 0;
            long stringFormatProcessor = 0;
            long stringBufferProcessor = 0;
            Set<ProcessorCallable> processorCallables = new HashSet<>();
            for (int index = 0; index < Integer.parseInt(args[1]); index++) {
                processorCallables.add(new ProcessorCallable(new StringConcatenationProcessor(index, args[0], args[2])));
                processorCallables.add(new ProcessorCallable(new StringBufferProcessor(index, args[0], args[2])));
                processorCallables.add(new ProcessorCallable(new JAXBProcessor(index, args[0], args[2])));
                processorCallables.add(new ProcessorCallable(new JSONProcessor(index, args[0], args[2])));
                processorCallables.add(new ProcessorCallable(new StringFormatProcessor(index, args[0], args[2])));
                processorCallables.add(new ProcessorCallable(new StringBuilderProcessor(index, args[0], args[2])));
            }
            ExecutorService executorService = Executors.newFixedThreadPool(THREADPOOL_SIZE);
            CompletionService<ProcessorCallableResult> executorCompletionService = new ExecutorCompletionService<>(executorService);
            Set<Future<ProcessorCallableResult>> futuresList = new HashSet<>();
            for (ProcessorCallable current : processorCallables) {
                futuresList.add(executorCompletionService.submit(current));
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("processor, start index, time (ms), end index");
            for (int i = 0; i < futuresList.size(); i++) {
                ProcessorCallableResult processor = executorCompletionService.take().get();
                stringBuilder.append(System.lineSeparator()).append(processor.getMessage()).append(",").append(i);
                switch (processor.getProcessor().getClass().getSimpleName()) {
                    case StringConcatenationProcessor.OUTPUT_FILENAME_SUFFIX:
                        stringConcatenationProcessor = stringConcatenationProcessor + processor.getTime();
                        break;
                    case StringBuilderProcessor.OUTPUT_FILENAME_SUFFIX:
                        stringBuilderProcessor = stringBuilderProcessor + processor.getTime();
                        break;
                    case JAXBProcessor.OUTPUT_FILENAME_SUFFIX:
                        jaxbProcessor = jaxbProcessor + processor.getTime();
                        break;
                    case JSONProcessor.OUTPUT_FILENAME_SUFFIX:
                        jsonProcessor = jsonProcessor + processor.getTime();
                        break;
                    case StringFormatProcessor.OUTPUT_FILENAME_SUFFIX:
                        stringFormatProcessor = stringFormatProcessor + processor.getTime();
                        break;
                    case StringBufferProcessor.OUTPUT_FILENAME_SUFFIX:
                        stringBufferProcessor = stringBufferProcessor + processor.getTime();
                        break;
                }
            }
            LOGGER.info("Open " + args[0] + " file, generate object with JAXB, sum all integers, append the result, and save the file, repeated " + args[1] + " times, took " + jaxbProcessor + "ms");
            LOGGER.info("Open " + args[0] + " file, generate object with JSON, append the result, and save the file using JSONMapper, repeated " + args[1] + " times, took " + jsonProcessor + "ms");
            LOGGER.info("Open " + args[0] + " file, sum all integers extracted with regex, append the result, and save the file concatenating strings, repeated " + args[1] + " times, took " + stringConcatenationProcessor + "ms");
            LOGGER.info("Open " + args[0] + " file, sum all integers extracted with regex, append the result, and save the file using StringBuffer, repeated " + args[1] + " times, took " + stringBufferProcessor + "ms");
            LOGGER.info("Open " + args[0] + " file, sum all integers extracted with regex, append the result, and save the file using StringBuilder, repeated " + args[1] + " times, took " + stringBuilderProcessor + "ms");
            LOGGER.info("Open " + args[0] + " file, sum all integers extracted with regex, append the result, and save the file using String.format, repeated " + args[1] + " times, took " + stringFormatProcessor + "ms");
            executorService.shutdown();
            FileUtils.getInstance().saveStringAsFile(args[2] + File.separator + "outputFile.csv", stringBuilder.toString());            
            LOGGER.info("That's all folks!...");
        } else {
            LOGGER.info("Usage: java -jar ExecutorCompletionPerformanceTest <file> <iterations>");
            LOGGER.info("You can save to file redirecting the output:\njava -jar SequentialPerformanceTest <file> <iterations> > <destination>");
        }
    }

}
