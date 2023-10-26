module sceneController {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens sceneController to javafx.fxml;
    opens controllers to javafx.fxml;
    exports controllers;
    exports sceneController;
}