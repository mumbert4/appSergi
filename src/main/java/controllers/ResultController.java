package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultController implements Initializable {



    @FXML
    private ImageView expectedGraph;

    @FXML
    private ImageView observedGraph;

    @FXML
    private Label title;

    public String aux="";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        System.out.println("Iniciando nueva escena");
        expectedGraph.setImage(new Image("file:" +System.getProperty("user.dir")+"/grafico1.png"));
        observedGraph.setImage(new Image("file:" +System.getProperty("user.dir")+"/grafico2.png"));
    }

    public void setTitle(String aux){
        title.setText(aux);
    }
}
