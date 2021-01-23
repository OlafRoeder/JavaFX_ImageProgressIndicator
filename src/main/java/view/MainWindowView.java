package view;

import application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.NonNull;

public class MainWindowView {

    private final Application application;
    private final MainWindowViewModel viewModel;

    @FXML
    private Label labelIdentifier;

    public MainWindowView(@NonNull Application application, MainWindowViewModel viewModel) {
        this.application = application;
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        // place propertybindings here
        labelIdentifier.textProperty().bind(viewModel.textProperty());
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