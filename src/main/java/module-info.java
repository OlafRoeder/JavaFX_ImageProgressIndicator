module TODO.Standard.Java.FX.Application.main {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires static lombok;

    requires org.slf4j;

    exports application to javafx.graphics;

    opens view to javafx.fxml;
}