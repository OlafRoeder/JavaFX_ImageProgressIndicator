module ImageProgressIndicator.Application.main {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires static lombok;

    requires org.slf4j;
    requires org.apache.logging.log4j;

    exports application to javafx.graphics;
    exports progressindicator to javafx.fxml;

    opens view to javafx.fxml;
    opens progressindicator to javafx.fxml;
}