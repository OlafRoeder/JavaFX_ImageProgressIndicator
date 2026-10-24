module de.olaf_roeder.ImageProgressIndicator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    exports de.olaf_roeder.progressindicator;
    opens de.olaf_roeder.progressindicator to javafx.fxml;
}
