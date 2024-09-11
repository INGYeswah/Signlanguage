package com.mimic.mimic;

import Model.CRUDFirebase;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class GameOverController implements Initializable {

    @FXML
    private Button bn1;

    @FXML
    private Button bn2;

    @FXML
    private Button bn3;

    @FXML
    private Label nickname;

    @FXML
    private Circle circImgInicio;
    
    @FXML
    private Label score;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        score.setText("Puntuacion: " + OsuHardController.puntuacion);
        
        if (App.osu == 1) {
            bn1.setText("ENDLESS AGAIN");
            bn3.setDisable(false);
        } else {
            bn1.setText("PRACTICE AGAIN");
            bn3.setDisable(true);
        }
        CRUDFirebase.obtenerDatos(App.db, App.player.getEmail());
        nickname.setText(App.player.getNickname());
        Image completeIcon = new Image(getClass().getResourceAsStream("/images\\perfil" + App.player.getFoto() + ".png"));
        circImgInicio.setFill(new ImagePattern(completeIcon));
    }

    public void Osu() throws IOException {
        if (App.osu == 1) {
            App.setRoot("osuHard");
        } else {
            App.setRoot("osu");
        }
    }

    public void Atras() throws IOException {
        App.setRoot("osuSelect");
    }

    public void Puntuation() throws IOException {
        App.setRoot("puntuacion");
    }

}
