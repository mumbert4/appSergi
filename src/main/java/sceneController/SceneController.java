package sceneController;

import controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    public static SceneController instance;
    public Scene main;
    public Stage stage;
    public static SceneController getInstance() throws IOException {
        if(instance==null) instance = new SceneController();
        return instance;
    }
    private SceneController() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("main.fxml"));
        this.main = new Scene(fxmlLoader.load(), 320, 240);
    }
}
