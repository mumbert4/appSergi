package com.example.appsergi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable {

    @FXML
    private ChoiceBox<String> barrioChoice;

    @FXML
    private ImageView dayGraph;

    @FXML
    private ComboBox<String> horarioChoice;

    @FXML
    private Button importButton;

    @FXML
    private TextArea infoText;

    @FXML
    private ImageView meanGraph;

    @FXML
    private Label fileInfo;

    @FXML
    private Label file;
    @FXML
    void importCSV(ActionEvent event) throws InterruptedException, MalformedURLException {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Elige CSV");
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("CSV","*.csv")
//        );
//        File f= fileChooser.showOpenDialog(null);
//
//
//        if(f==null){
//            fileInfo.setText("No has elegido ningun fichero");
//            file.setText("");
//        }
//        else{
//            System.out.println("Fitxer CSV seleccionat: " + f.getAbsolutePath());
//            fileInfo.setText("Fichero elegido:");
//            file.setText(f.getName());
//        }
        runPython();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        barrioChoice.getItems().add("Sants");
        barrioChoice.getItems().add("Lluc");
        barrioChoice.getItems().add("Tumadre");
        barrioChoice.getItems().add("Prova");
    }



    void runPython() throws InterruptedException, MalformedURLException {
        Process process = null;
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        try{
            String script= "src/main/python/mi_script.py";
            String command = "python3 " + script;
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
            processBuilder.redirectErrorStream(true);

            process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            BufferedReader reader= new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = reader.readLine())!=null){
                System.out.println(line);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        int n= process.waitFor();
        System.out.println("Estado del proceso: " + n);
        if(n==0){
            System.out.println("Terminado con exito");
            dayGraph.setImage(new Image("file:" +System.getProperty("user.dir")+"/grafico1.png"));
            meanGraph.setImage(new Image("file:" +System.getProperty("user.dir")+"/grafico2.png"));
        }

    }
}

