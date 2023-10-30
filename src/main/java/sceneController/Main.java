package sceneController;

import controllers.MainController;
import controllers.ResultController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {




    @Override
    public void start(Stage stage) throws IOException {

        SceneController.getInstance().mainStage=stage;
        stage.setTitle("i-ViSta4Bike");
        stage.setScene(SceneController.instance.main);
        stage.setWidth(1375);
        stage.setHeight(970);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}