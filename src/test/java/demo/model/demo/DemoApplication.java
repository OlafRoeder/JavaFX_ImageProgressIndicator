package demo.model.demo;

import demo.application.ApplicationType;
import demo.concurrency.GlobalExecutorService;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoApplication implements ApplicationType {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    @Override
    public void printApplicationType() {
        logger.info("Starting demo demo.application");
    }

    @Override
    public void execute(Task<?> task) {
        GlobalExecutorService.submit(task);
    }
}
