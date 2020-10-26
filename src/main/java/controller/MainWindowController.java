package controller;

import application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainWindowController extends VBox {

    private static final Logger logger = LoggerFactory.getLogger(MainWindowController.class);

    private final Application application;

    public MainWindowController(@NonNull Application application) {
        this.application = application;
    }

    @FXML
    private void onQuit() {
        application.quit();
    }

    @FXML
    public void onTask() {

        application.execute(new Task<Void>() {

            @Override
            protected Void call() {

                logger.debug("long running task start");

                return null;
            }

            @Override
            protected void succeeded() {
                logger.debug("long running task succeeded");
            }
        });
    }
}