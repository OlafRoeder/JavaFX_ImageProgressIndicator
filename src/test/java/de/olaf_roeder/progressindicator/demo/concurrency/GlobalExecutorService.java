package de.olaf_roeder.progressindicator.demo.concurrency;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.*;

/**
 * An {@link ExecutorService} that handles shutdown when JVM stops, logs
 * uncaught Exceptions and supports Thread renaming. <br>
 * API: <br>
 * {@link #submit(Runnable)}<br>
 */
public class GlobalExecutorService {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExecutorService.class);

    private static final boolean ASYNC_MODE = true;
    private static final long TIMEOUT = 1000;

    private static final UncaughtExceptionHandler UNCAUGHT_EXCEPTION_HANDLER =
            (t, e) -> logger.error("Thread " + t.getName() + " threw exception " + e.getClass(), e);

    /**
     * Same as {@link Executors#newWorkStealingPool()} plus custom
     * {@link UncaughtExceptionHandler}.
     */
    private static final ExecutorService EXECUTOR = new ForkJoinPool(Runtime.getRuntime().availableProcessors(),
            ForkJoinPool.defaultForkJoinWorkerThreadFactory, UNCAUGHT_EXCEPTION_HANDLER, ASYNC_MODE);

    static {

        Runtime.getRuntime().addShutdownHook(new Thread("GlobalExecutorService Shutdown Hook") {

            @Override
            public void run() {
                shutdown();
            }
        });
    }

    private GlobalExecutorService() {
        // singleton class
    }

    /**
     * Submits a {@link Runnable} task to the underlying {@link ExecutorService}.
     * The task will be executed asynchronously, so there is no guarantee that
     * subsequent submitted tasks are run in order.
     *
     * @param task A {@link Runnable} object.
     *
     * @return A {@link Future}. The Future's get method will return null upon successful completion.
     */
    public static Future<?> submit(@NonNull Runnable task) {

        if (EXECUTOR.isShutdown())
            throw new IllegalStateException(
                    "Executor was shut down. Do not call GlobalExecutorService.submit(Runnable) after GlobalExecutorService.shutdown()!");

        return EXECUTOR.submit(task);
    }

    private static void shutdown() {

        logger.debug("Shutting down " + EXECUTOR);

        EXECUTOR.shutdown();

        try {

            if (!EXECUTOR.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS)) {

                EXECUTOR.shutdownNow().forEach(task -> logger.debug("Still running task: " + task.getClass()));

                if (!EXECUTOR.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS))
                    logger.error(EXECUTOR + " could not shut down properly.");
            }

        } catch (InterruptedException e) {
            EXECUTOR.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}