package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.demo.DemoApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.MainWindowView;

import java.net.URL;

public class Main extends Application {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private application.Application application;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() {
        logger.debug("initialize application");
        application = ApplicationFactory.createApplication(new DemoApplication());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        logger.debug("startup application");

        // full screen settings
//        primaryStage.initStyle(StageStyle.UNDECORATED);
//        primaryStage.setMaximized(true);

        // Load root layout from fxml file.
        URL resource = Main.class.getResource("/view/MainWindowView.fxml");

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(resource);
        loader.setControllerFactory(application.getFxmlControllerFactory());

        Parent mainWindow = loader.load();

        Scene scene = new Scene(mainWindow);

        primaryStage.setScene(scene);

        //controller
        MainWindowView controller = loader.getController();

        primaryStage.show();
    }
}
