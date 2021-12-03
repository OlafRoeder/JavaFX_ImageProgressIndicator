package view;

import application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.NonNull;
import progressindicator.ImageProgressIndicator;

public class MainWindowView {

    private final Application application;
    private final MainWindowViewModel viewModel;

    @FXML
    private Label label;
    @FXML
    private ImageProgressIndicator progressIndicator;

    public MainWindowView(@NonNull Application application, MainWindowViewModel viewModel) {
        this.application = application;
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        label.textProperty().bind(viewModel.textProperty());

        progressIndicator.visibleProperty().bind(viewModel.progressVisibleProperty());
        progressIndicator.progressProperty().bind(viewModel.progressProperty());
        progressIndicator.progressPercentVisibleProperty().bind(viewModel.progressPercentVisibleProperty());
        progressIndicator.overlayVisibleProperty().set(false);

        progressIndicator.textProperty().set("Progress Indicator initialized");
        progressIndicator.progressProperty().addListener(observable -> progressIndicator.textProperty().set("Progress " + progressIndicator.getProgress() + "%"));
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