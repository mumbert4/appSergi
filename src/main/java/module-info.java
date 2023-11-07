module sceneController {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.json;
    requires org.apache.pdfbox;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens sceneController to javafx.fxml;
    opens controllers to javafx.fxml;
    exports controllers;
    exports sceneController;
}