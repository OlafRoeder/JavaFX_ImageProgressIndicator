package controller;

import application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(MainWindowController.class);

    private final Application application;

    public MainWindowController(@NonNull Application application) {
        this.application = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // place propertybindings here
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