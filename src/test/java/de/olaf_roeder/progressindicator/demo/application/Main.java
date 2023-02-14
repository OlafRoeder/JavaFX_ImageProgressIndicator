package de.olaf_roeder.progressindicator.demo.application;

import de.olaf_roeder.progressindicator.demo.model.demo.DemoApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class Main extends Application {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private de.olaf_roeder.progressindicator.demo.application.Application application;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() {
        logger.debug("initialize demo.application");
        application = ApplicationFactory.createApplication(new DemoApplication());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        logger.debug("startup demo.application");

        URL resource = Main.class.getResource("/de/olaf_roeder/progressindicator/demo/view/MainWindowView.fxml");

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(resource);
        loader.setControllerFactory(application.getFxmlControllerFactory());

        Parent mainWindow = loader.load();

        Scene scene = new Scene(mainWindow);

        primaryStage.setScene(scene);

        primaryStage.getIcons().addAll(
                new Image(Main.class.getResourceAsStream("/de/olaf_roeder/progressindicator/demo/img/info_16x16.png")),
                new Image(Main.class.getResourceAsStream("/de/olaf_roeder/progressindicator/demo/img/info_24x24.png")),
                new Image(Main.class.getResourceAsStream("/de/olaf_roeder/progressindicator/demo/img/info_32x32.png")),
                new Image(Main.class.getResourceAsStream("/de/olaf_roeder/progressindicator/demo/img/info_40x40.png")),
                new Image(Main.class.getResourceAsStream("/de/olaf_roeder/progressindicator/demo/img/info_48x48.png")),
                new Image(Main.class.getResourceAsStream("/de/olaf_roeder/progressindicator/demo/img/info_64x64.png")),
                new Image(Main.class.getResourceAsStream("/de/olaf_roeder/progressindicator/demo/img/info_128x128.png")),
                new Image(Main.class.getResourceAsStream("/de/olaf_roeder/progressindicator/demo/img/info_256x256.png"))
        );

        primaryStage.show();
    }
}