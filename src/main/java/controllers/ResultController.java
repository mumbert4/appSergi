package controllers;

import Functions.CreatePDF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.web.WebView;
public class ResultController implements Initializable {



    @FXML
    private ImageView expectedGraph;

    @FXML
    private ImageView observedGraph;

    @FXML
    private Label title;

    @FXML
    private WebView infoText;
    public String text="";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        System.out.println("Iniciando nueva escena");
        expectedGraph.setImage(new Image("file:" +System.getProperty("user.dir")+"/grafico1.png"));
        observedGraph.setImage(new Image("file:" +System.getProperty("user.dir")+"/grafico2.png"));

        StringBuilder sb = new StringBuilder(text);
        sb.append("<b>Texto informacion</b>");
        sb.append("<ul>" +
                    "<li>List item1</li>" +
                    "<li>List item2</li>" +
                    "<li>List item3</li>" +
                    "<li>List item4</li>" +
                 "</ul>"
        );
        text=sb.toString();
        System.out.printf(text);
        infoText.getEngine().loadContent(text);
    }



    @FXML
    void downloadPDF(ActionEvent event){
        System.out.printf("Download pdf");
        CreatePDF.getInstance().createPDF();
    }

    public void setTitle(String aux){
        title.setText(aux);
    }
}
