package demo.application;

import javafx.application.Platform;
import javafx.concurrent.Task;

public class Application {

    private final ApplicationType applicationType;

    Application(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public void execute(Task<?> task) {
        applicationType.execute(task);
    }

    public void quit() {
        Platform.exit();
    }

    FXMLControllerFactory getFxmlControllerFactory() {
        return new FXMLControllerFactory(this);
    }
}
