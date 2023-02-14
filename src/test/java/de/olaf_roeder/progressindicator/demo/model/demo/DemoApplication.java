package de.olaf_roeder.progressindicator.demo.model.demo;

import de.olaf_roeder.progressindicator.demo.application.ApplicationType;
import de.olaf_roeder.progressindicator.demo.concurrency.GlobalExecutorService;
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
