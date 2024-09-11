package com.mimic.mimic;

import Model.CRUDFirebase;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author yeswa
 */
public class OsuSelectController implements Initializable {

    @FXML
    private Label nickname;

    @FXML
    private Circle circImgInicio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CRUDFirebase.obtenerDatos(App.db, App.player.getEmail());
        nickname.setText(App.player.getNickname());
        Image completeIcon = new Image(getClass().getResourceAsStream("/images\\perfil" + App.player.getFoto() + ".png"));
        circImgInicio.setFill(new ImagePattern(completeIcon));
    }

    public void OsuPractice() throws IOException {
        App.setRoot("osu");
    }

    public void OsuHard() throws IOException {
        App.setRoot("osuHard");
    }

    public void Atras() throws IOException {
        App.setRoot("inicio");
    }

}
