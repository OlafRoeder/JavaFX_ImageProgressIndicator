package view;

import application.Application;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainWindowViewModel {

    private static final Logger logger = LoggerFactory.getLogger(MainWindowViewModel.class);

    private final StringProperty textProperty = new SimpleStringProperty("Go to menu and start a task.");

    private final Application application;

    private final BooleanProperty progressVisible = new SimpleBooleanProperty();
    private final BooleanProperty progressPercentVisible = new SimpleBooleanProperty(false);
    private final DoubleProperty progress = new SimpleDoubleProperty();

    public MainWindowViewModel(Application application) {
        this.application = application;
    }

    StringProperty textProperty() {
        return textProperty;
    }

    ReadOnlyBooleanProperty progressVisibleProperty() {
        return progressVisible;
    }

    ReadOnlyDoubleProperty progressProperty() {
        return progress;
    }

    ReadOnlyBooleanProperty progressPercentVisibleProperty() {
        return progressPercentVisible;
    }

    public void onTask() {

        application.execute(new Task<Void>() {

            @Override
            protected Void call() throws InterruptedException {

                logger.debug("long running task start");

                Platform.runLater(() -> {
                    textProperty.set("long running task start - indeterminate");
                    progress.set(-1D);
                    progressVisible.set(true);
                });

                Thread.sleep(2000);

                Platform.runLater(() -> {
                    textProperty.set("long running task start - determinate");
                    progressPercentVisible.set(true);
                });

                for (int i = 0; i <= 100; i++) {
                    int finalI = i;
                    Platform.runLater(() -> progress.set(finalI / 100D));
                    Thread.sleep(100);
                }

                Platform.runLater(() -> {
                    textProperty.set("long running task start - complete");
                    progressPercentVisible.set(false);
                });

                return null;
            }

            @Override
            protected void succeeded() {

                logger.debug("long running task succeeded");

                textProperty.set("long running task succeeded");
                progressVisible.set(false);
            }

            @Override
            protected void failed() {
                logger.error("Oh no, something bad happened!", getException());
            }
        });
    }
}