package local.tin.tests.execution.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import local.tin.tests.execution.services.threads.SampleCallable;
import local.tin.tests.execution.services.threads.SampleRunnable;
import org.apache.log4j.Logger;

/**
 *
 * @author benitodarder
 */
public class ExecutingRunnables {

    private static final Logger LOGGER = Logger.getLogger(ExecutingRunnables.class);
    private static final int THREADPOOL_SIZE = 4;
    private static final int NUMBER_OF_TASKS = 15;
    private static final long MAX_SLEEPING_TIME = 5000;

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     * @throws java.util.concurrent.ExecutionException
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        LOGGER.info("Let's create some callables and run them through ExecutorService");
        LOGGER.info("Thread pool size: " + THREADPOOL_SIZE + "; number of tasks: " + NUMBER_OF_TASKS + "; max. sleeping time: " + MAX_SLEEPING_TIME);
        ExecutorService executorService = Executors.newFixedThreadPool(THREADPOOL_SIZE);
        List<Future> futures = new ArrayList<>();
        long t0 = System.currentTimeMillis();
        for (int i = 0; i < NUMBER_OF_TASKS; i++) {
            long sleepTime = (long) (Math.random() * (MAX_SLEEPING_TIME + 1));
            SampleRunnable newCallable = new SampleRunnable(String.valueOf(i), sleepTime);
            futures.add(executorService.submit(newCallable));
        }
        LOGGER.info("Waiting for tasks to end...");
        boolean allDone = false;
        while (!allDone) {
            allDone = true;
            for (Future current : futures) {
                allDone = allDone && current.isDone();
            }
        }
        LOGGER.info("Tasks terminated...");
        executorService.shutdown();
        LOGGER.info("Execution time: " + (System.currentTimeMillis() - t0));
    }

}
