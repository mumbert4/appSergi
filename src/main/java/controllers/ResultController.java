package controllers;

import Functions.CreatePDF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.web.WebView;
import org.json.JSONArray;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ResultController implements Initializable {



    @FXML
    private ImageView expectedGraph;

    @FXML
    private ImageView observedGraph;

    @FXML
    private Label eventType;
    @FXML
    private Label title;

    @FXML
    private WebView infoText;
    public String text="";
    public String id="";//barrio(03) y luego franja(03); ej:0303


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        System.out.println("Iniciando nueva escena");
//        expectedGraph.setImage(new Image("file:" +System.getProperty("user.dir")+"/grafico1.png"));
//        observedGraph.setImage(new Image("file:" +System.getProperty("user.dir")+"/grafico2.png"));

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

    public void setId(String id){
        this.id = id;
        System.out.println("Id: " + id);
//        expectedGraph.setImage(new Image("file:" +System.getProperty("user.dir")+"/grafico1.png"));
//        observedGraph.setImage(new Image("file:" +System.getProperty("user.dir")+"/grafico2.png"));
        expectedGraph.setImage(new Image("file:" +"/home/miquel/Documentos/Solucion/Output"+"/Graficos_average_"+id+".png"));
        observedGraph.setImage(new Image("file:" +"/home/miquel/Documentos/Solucion/Output"+"/Graficos_day_"+id+".png"));

        JSONParser jsonParser = new JSONParser();
        try{
            //FileReader reader = new FileReader("/home/miquel/Documentos/Solucion/Output/resultado_"+id+".json");
            FileReader reader = new FileReader("/home/miquel/Documentos/Solucion/Output/aux.json");
            JSONTokener tokener = new JSONTokener(reader);
            JSONArray jsonArray = new JSONArray(tokener);

            String respuesta = jsonArray.getJSONObject(0).getString("respuesta");
            System.out.println("Respuesta: " + respuesta);

            if(respuesta.equals("Unexpected")) eventType.setText("Unexpected event");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
