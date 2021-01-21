package view;

import application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.NonNull;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowView implements Initializable {

    private final Application application;
    private final MainWindowViewModel viewModel;

    @FXML
    private Label labelIdentifier;

    public MainWindowView(@NonNull Application application, MainWindowViewModel viewModel) {
        this.application = application;
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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