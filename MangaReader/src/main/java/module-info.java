module com.example.mangareader {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;
    requires org.json;

    opens com.example.mangareader to javafx.fxml;
    exports com.example.mangareader;
    exports controllers;
    opens controllers to javafx.fxml;
}