module de.olaf_roeder.ImageProgressIndicator {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // requires static: compile-time-only dependencies
    requires static lombok;

    // requires: runtime and compile-time dependencies
    requires org.slf4j;
    requires org.apache.logging.log4j;

    // exports: declare accessible API
    exports de.olaf_roeder.progressindicator;

}