package com.example.appsergi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable {

    @FXML
    private ChoiceBox<String> barrioChoice;

    @FXML
    private ImageView dayGraph;

    @FXML
    private ChoiceBox<String> horarioChoice;

    @FXML
    private Button importButton;

    @FXML
    private TextArea infoText;

    @FXML
    private ImageView meanGraph;

    @FXML
    void importCSV(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        barrioChoice.getItems().add("Sants");
        barrioChoice.getItems().add("Lluc");
        barrioChoice.getItems().add("Tumadre");
        barrioChoice.getItems().add("Prova");
    }
}
