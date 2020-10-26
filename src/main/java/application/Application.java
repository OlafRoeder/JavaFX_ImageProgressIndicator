package application;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Application {

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

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
