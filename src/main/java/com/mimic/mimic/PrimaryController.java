package com.mimic.mimic;

import Model.CRUDFirebase;
import Model.Player;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class PrimaryController implements Initializable {

    @FXML
    private Circle circImgInicio;

    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image completeIcon = new Image(getClass().getResourceAsStream("/images\\completeIcon.png"));
        circImgInicio.setFill(new ImagePattern(completeIcon));
    }

    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("register");
    }

    public void logIn() throws IOException {
        if (("".equals(email.getText().toString().trim())) || ("".equals(password.getText().toString().trim()))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alerta");
            alert.setHeaderText(null);
            alert.setContentText("Rellene todos los campos");
            alert.showAndWait();
            return;
        }

        if (!CRUDFirebase.obtenerDato(App.db, email.getText().toString())) {
            if (password.getText().toString().equals(CRUDFirebase.obtenerDato(App.db, email.getText().toString(), "password"))) {
                App.player = new Player();
                App.player.setEmail(email.getText());
                App.setRoot("intrucciones"); // Aqui continuo 
                System.out.println("Se ha entrado al juego");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alerta");
                alert.setHeaderText(null);
                alert.setContentText("Su correo o contraseña es incorrecto");
                alert.showAndWait();
                password.setText("");
                email.setText("");
                return;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alerta");
            alert.setHeaderText(null);
            alert.setContentText("Su correo o contraseña es incorrecto");
            alert.showAndWait();
            password.setText("");
            email.setText("");
        }
    }

}
