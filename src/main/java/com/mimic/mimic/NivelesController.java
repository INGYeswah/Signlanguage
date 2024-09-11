package com.mimic.mimic;

import Model.CRUDFirebase;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class NivelesController implements Initializable {

    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    @FXML
    private Button btn9;

    @FXML
    private Label nickname;

    @FXML
    private Circle circImgInicio;

    String[] letras = new String[]{
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    public static String[] letrasRango;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        CRUDFirebase.obtenerDatos(App.db, App.player.getEmail());
        nickname.setText(App.player.getNickname());
        Image completeIcon = new Image(getClass().getResourceAsStream("/images\\perfil" + App.player.getFoto() + ".png"));
        circImgInicio.setFill(new ImagePattern(completeIcon));

        int level = App.player.getLevel();

        btn1.setDisable(false);
        btn2.setDisable(true);
        btn3.setDisable(true);
        btn4.setDisable(true);
        btn5.setDisable(true);
        btn6.setDisable(true);
        btn7.setDisable(true);
        btn8.setDisable(true);
        btn9.setDisable(true);

        if (level >= 2) {
            btn2.setDisable(false);
        }
        if (level >= 3) {
            btn3.setDisable(false);
        }
        if (level >= 4) {
            btn4.setDisable(false);
        }
        if (level >= 5) {
            btn5.setDisable(false);
        }
        if (level >= 6) {
            btn6.setDisable(false);
        }
        if (level >= 7) {
            btn7.setDisable(false);
        }
        if (level >= 8) {
            btn8.setDisable(false);
        }
        if (level >= 9) {
            btn9.setDisable(false);
        }

    }

    public void level1() throws IOException {
        App.levelOn = 1;
        App.nivelActual = 1;
        letrasRango = Arrays.copyOfRange(letras, App.levelOn - 1, App.levelOn + 2);
        App.setRoot("introLetra");
    }

    public void level2() throws IOException {
        App.levelOn = 2;
        App.nivelActual = 2;
        letrasRango = Arrays.copyOfRange(letras, 3 * (App.levelOn - 1), (App.levelOn * 3));
        App.setRoot("introLetra");
    }

    public void level3() throws IOException {
        App.levelOn = 3;
        App.nivelActual = 3;
        letrasRango = Arrays.copyOfRange(letras, 3 * (App.levelOn - 1), (App.levelOn * 3));
        App.setRoot("introLetra");
    }

    public void level4() throws IOException {
        App.levelOn = 4;
        App.nivelActual = 4;
        letrasRango = Arrays.copyOfRange(letras, 3 * (App.levelOn - 1), (App.levelOn * 3));
        App.setRoot("introLetra");
    }

    public void level5() throws IOException {
        App.levelOn = 5;
        App.nivelActual = 5;
        letrasRango = Arrays.copyOfRange(letras, 3 * (App.levelOn - 1), (App.levelOn * 3));
        App.setRoot("introLetra");
    }

    public void level6() throws IOException {
        App.levelOn = 6;
        App.nivelActual = 6;
        letrasRango = Arrays.copyOfRange(letras, 3 * (App.levelOn - 1), (App.levelOn * 3));
        App.setRoot("introLetra");
    }

    public void level7() throws IOException {
        App.levelOn = 7;
        App.nivelActual = 7;
        letrasRango = Arrays.copyOfRange(letras, 3 * (App.levelOn - 1), (App.levelOn * 3));
        App.setRoot("introLetra");
    }

    public void level8() throws IOException {
        App.levelOn = 8;
        App.nivelActual = 8;
        letrasRango = Arrays.copyOfRange(letras, 3 * (App.levelOn - 1), (App.levelOn * 3));
        App.setRoot("introLetra");
    }

    public void level9() throws IOException {
        App.levelOn = 9;
        App.nivelActual = 9;
        letrasRango = Arrays.copyOfRange(letras, 3 * (App.levelOn - 1), (App.levelOn * 3) - 1);
        App.setRoot("introLetra");
    }

    public void atras() throws IOException {
        App.setRoot("inicio");
    }

}
