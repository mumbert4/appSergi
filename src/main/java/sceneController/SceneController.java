package sceneController;

import controllers.MainController;
import controllers.ResultController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class SceneController {

    private static SceneController instance;
    public Scene main;
    public Stage mainStage;


    public HashMap<String,Stage> stages;
    public static SceneController getInstance() throws IOException {
        if(instance==null) instance = new SceneController();
        return instance;
    }
    private SceneController() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("main.fxml"));
        this.main = new Scene(fxmlLoader.load(), 320, 240);
        this.stages = new HashMap<>();
    }


    public void close(){
        this.mainStage.close();
    }
    public void setScene(String scene, ArrayList<String> neighbours, ArrayList<String> hours) throws IOException {
        FXMLLoader fxmlLoader;
        Stage stage;
        for(String n : neighbours){
            for(String h: hours){
                fxmlLoader = new FXMLLoader(ResultController.class.getResource("result.fxml"));
                Stage aux = new Stage();
                aux.setScene(new Scene(fxmlLoader.load(), 320, 240));
                aux.setWidth(1375);
                aux.setHeight(970);
                aux.setTitle(n + " " + h);
                ResultController r = fxmlLoader.getController();
                r.setTitle(n + " " + h);
                aux.show();
            }
        }
//        if(scene.equals("Result")){
//
//            FXMLLoader fxmlLoader = new FXMLLoader(ResultController.class.getResource("result.fxml"));
////            this.main = new Scene(fxmlLoader.load(), 320, 240);
////            this.mainStage.setScene(this.main);
//
//
//
//            fxmlLoader = new FXMLLoader(ResultController.class.getResource("result.fxml"));
//            Stage aux = new Stage();
//            aux.setScene(new Scene(fxmlLoader.load(), 320, 240));
//            aux.setWidth(1375);
//            aux.setHeight(970);
//            aux.show();
//
//        }
    }
}
