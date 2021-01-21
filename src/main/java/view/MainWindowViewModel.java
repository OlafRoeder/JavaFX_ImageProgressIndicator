package view;

import application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainWindowViewModel {

    private static final Logger logger = LoggerFactory.getLogger(MainWindowViewModel.class);

    private final StringProperty textProperty = new SimpleStringProperty("Hello, World!");

    private final Application application;

    public MainWindowViewModel(Application application) {
        this.application = application;
    }

    public StringProperty textProperty() {
        return textProperty;
    }

    public void onTask() {

        application.execute(new Task<String>() {

            @Override
            protected String call() {

                logger.debug("long running task start");

                return "long running task finished";
            }

            @Override
            protected void failed() {
                //override this for errorhandling
            }

            @Override
            protected void cancelled() {
                //override this to cancel
            }

            @Override
            protected void succeeded() {

                logger.debug("long running task succeeded");

                textProperty.set(getValue());
            }
        });
    }
}