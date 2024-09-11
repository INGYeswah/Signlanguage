package com.mimic.mimic;

import Model.CRUDFirebase;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class SetupSessionController implements Initializable {

    @FXML
    private Circle circImgInicio;

    @FXML
    private TextField nickname;
    @FXML
    private RadioButton rb1;
    @FXML
    private RadioButton rb2;
    @FXML
    private RadioButton rb3;
    @FXML
    private RadioButton rb4;
    @FXML
    private RadioButton rb5;
    @FXML
    private RadioButton rb6;

    private int iconImageNumber;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image completeIcon = new Image(getClass().getResourceAsStream("/images\\completeIcon.png"));
        circImgInicio.setFill(new ImagePattern(completeIcon));

        Image pefil1 = new Image(getClass().getResourceAsStream("/images\\perfil1.png"));
        Image pefil2 = new Image(getClass().getResourceAsStream("/images\\perfil2.png"));
        Image pefil3 = new Image(getClass().getResourceAsStream("/images\\perfil3.png"));
        Image pefil4 = new Image(getClass().getResourceAsStream("/images\\perfil4.png"));
        Image pefil5 = new Image(getClass().getResourceAsStream("/images\\perfil5.png"));
        Image pefil6 = new Image(getClass().getResourceAsStream("/images\\perfil6.png"));

        animacionRadioButton(rb1, pefil1);
        animacionRadioButton(rb2, pefil2);
        animacionRadioButton(rb3, pefil3);
        animacionRadioButton(rb4, pefil4);
        animacionRadioButton(rb5, pefil5);
        animacionRadioButton(rb6, pefil6);
    }

    public void setNicknameAndFoto() throws IOException {

        if ("".equals(nickname.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alerta");
            alert.setHeaderText(null);
            alert.setContentText("Por favor seleccione un nickname");
            alert.showAndWait();
        } else {
            System.out.println(iconImageNumber);
            CRUDFirebase.modificarDato(App.db, App.player.getEmail(), "nickname", nickname.getText().toString());
            CRUDFirebase.modificarDato(App.db, App.player.getEmail(), "foto", iconImageNumber);
            App.setRoot("inicio");
        }

    }

    public void animacionRadioButton(RadioButton rb, Image completeIcon) {
        ImageView imageView = new ImageView(completeIcon);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(false);

        // Configurar el RadioButton
        rb.setGraphic(imageView);
        rb.setStyle("-fx-background-color: transparent; -fx-padding: 0; -fx-border-width: 0;");
        rb.getStyleClass().remove("radio-button");
        rb.getStyleClass().add("toggle-button");

        Circle clip = new Circle(50); // Radio del círculo
        clip.setCenterX(50); // Centro X del círculo
        clip.setCenterY(50); // Centro Y del círculo
        rb.setClip(clip);

        // Crear animaciones de escala para el RadioButton
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), rb);
        scaleUp.setToX(1.1); // Aumentar el tamaño al 120%
        scaleUp.setToY(1.1); // Aumentar el tamaño al 120%

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), rb);
        scaleDown.setToX(1.0); // Volver al tamaño original
        scaleDown.setToY(1.0); // Volver al tamaño original

        // Listener para detectar el cambio de selección
        rb.selectedProperty().addListener((observable, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                scaleUp.play(); // Reproducir animación de aumento
                iconImageNumber = Integer.parseInt((rb.getText().toString()));
            } else {
                scaleDown.play(); // Reproducir animación de reducción
            }
        });
    }

}
