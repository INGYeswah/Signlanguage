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

public class SecondaryController implements Initializable {

    @FXML
    private Circle circImgInicio;
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField repassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image completeIcon = new Image(getClass().getResourceAsStream("/images\\completeIcon.png"));
        circImgInicio.setFill(new ImagePattern(completeIcon));
    }

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

    @FXML
    public void Register() throws IOException {

        if (("".equals(name.getText().toString().trim())) || ("".equals(email.getText().toString().trim())) || ("".equals(password.getText().toString().trim()) || ("".equals(repassword.getText().toString().trim())))) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alerta");
            alert.setHeaderText(null);
            alert.setContentText("Rellene todos los campos");
            alert.showAndWait();

            return;
        }

        if ((password.getText().toString()).equals(repassword.getText().toString())) {
            if (CRUDFirebase.obtenerDato(App.db, email.getText().toString())) {
                App.player = new Player("", name.getText().toString(), email.getText().toString(), password.getText().toString(), 1, 1);
                CRUDFirebase.addPlayerFirebase(App.player, App.db);
                password.setText("");
                repassword.setText("");
                email.setText("");
                name.setText("");
                App.setRoot("setupSession");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alerta");
                alert.setHeaderText(null);
                alert.setContentText("El email ya ha sido registrado por favor seleccione otro");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alerta");
            alert.setHeaderText(null);
            alert.setContentText("La contraseña no es la misma por favor vuelva a intentarlo");
            alert.showAndWait();
        }
    }

}
