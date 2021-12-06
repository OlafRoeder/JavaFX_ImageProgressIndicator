package view;

import application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import lombok.NonNull;
import progressindicator.ImageProgressIndicator;
import progressindicator.ORIENTATION;

public class MainWindowView {

    private final Application application;
    private final MainWindowViewModel viewModel;

    @FXML
    private HBox content;
    @FXML
    private Label label;
    @FXML
    private ImageProgressIndicator determinateProgressIndicator;
    @FXML
    private ImageProgressIndicator indeterminateProgressIndicator;
    @FXML
    private ImageProgressIndicator verticalProgressIndicator;

    public MainWindowView(@NonNull Application application, MainWindowViewModel viewModel) {
        this.application = application;
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        label.textProperty().bind(viewModel.textProperty());

        String url = ImageProgressIndicator.class.getResource("exampleAnimation.gif").toExternalForm();
        ImageProgressIndicator imageProgressIndicatorFromJava = new ImageProgressIndicator(url, 1, ORIENTATION.HORIZONTAL);
        imageProgressIndicatorFromJava.visibleProperty().bind(viewModel.progressVisibleProperty());
        imageProgressIndicatorFromJava.progressProperty().bind(viewModel.progressProperty());
        Platform.runLater(() -> {
            imageProgressIndicatorFromJava.progressPercentVisibleProperty().set(false);
            imageProgressIndicatorFromJava.overlayVisibleProperty().set(false);
        });

        content.getChildren().add(0, imageProgressIndicatorFromJava);

        determinateProgressIndicator.visibleProperty().bind(viewModel.progressVisibleProperty());
        determinateProgressIndicator.progressProperty().bind(viewModel.progressProperty());
        determinateProgressIndicator.progressPercentVisibleProperty().bind(viewModel.progressPercentVisibleProperty());

        determinateProgressIndicator.textProperty().set("Progress Indicator initialized");
        determinateProgressIndicator.progressProperty().addListener(observable -> determinateProgressIndicator.textProperty().set("Progress " + determinateProgressIndicator.getProgress() * 100 + "%"));

        indeterminateProgressIndicator.visibleProperty().bind(viewModel.progressVisibleProperty());
        indeterminateProgressIndicator.progressProperty().set(ProgressIndicator.INDETERMINATE_PROGRESS);
        Platform.runLater(() -> indeterminateProgressIndicator.progressPercentVisibleProperty().set(false));

        indeterminateProgressIndicator.textProperty().set(" ");
        indeterminateProgressIndicator.progressProperty().addListener(observable -> indeterminateProgressIndicator.textProperty().set("Progress " + determinateProgressIndicator.getProgress() + "%"));

        verticalProgressIndicator.visibleProperty().bind(viewModel.progressVisibleProperty());
        verticalProgressIndicator.progressProperty().bind(viewModel.progressProperty());
        verticalProgressIndicator.progressPercentVisibleProperty().bind(viewModel.progressPercentVisibleProperty());

        verticalProgressIndicator.textProperty().set("Progress Indicator initialized");
        verticalProgressIndicator.progressProperty().addListener(observable -> verticalProgressIndicator.textProperty().set("Progress " + determinateProgressIndicator.getProgress() * 100 + "%"));
    }

    @FXML
    private void onQuit() {
        application.quit();
    }

    @FXML
    public void onTask() {
        viewModel.onTask();
    }
}