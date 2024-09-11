package com.mimic.mimic;

import Model.CRUDFirebase;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author yeswa
 */
public class AbecedarioController implements Initializable {

    @FXML
    private ImageView im1;
    @FXML
    private ImageView im2;
    @FXML
    private ImageView im3;
    @FXML
    private ImageView im4;
    @FXML
    private ImageView im5;
    @FXML
    private ImageView im6;
    @FXML
    private ImageView im7;
    @FXML
    private ImageView im8;
    @FXML
    private ImageView im9;
    @FXML
    private ImageView im10;
    @FXML
    private ImageView im11;
    @FXML
    private ImageView im12;
    @FXML
    private ImageView im13;
    @FXML
    private ImageView im14;
    @FXML
    private ImageView im15;
    @FXML
    private ImageView im16;
    @FXML
    private ImageView im17;
    @FXML
    private ImageView im18;
    @FXML
    private ImageView im19;
    @FXML
    private ImageView im20;
    @FXML
    private ImageView im21;
    @FXML
    private ImageView im22;
    @FXML
    private ImageView im23;
    @FXML
    private ImageView im24;
    @FXML
    private ImageView im25;
    @FXML
    private ImageView im26;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Array de ImageView
        ImageView[] imageViews = {im1, im2, im3, im4, im5, im6, im7, im8, im9, im10,
            im11, im12, im13, im14, im15, im16, im17, im18,
            im19, im20, im21, im22, im23, im24, im25, im26};

        // Configurar propiedades para ajustar imagen
        for (ImageView imageView : imageViews) {
            imageView.setPreserveRatio(true); // Mantener la relación de aspecto
            imageView.setFitWidth(100);        // Ajustar el ancho máximo (puedes ajustar este valor)
            imageView.setFitHeight(100);       // Ajustar la altura máxima (puedes ajustar este valor)
        }

        // Asignar imágenes a cada ImageView
        for (int i = 0; i < imageViews.length; i++) {
            char letra = (char) ('A' + i); // 'A' para 0, 'B' para 1, ..., 'Z' para 25
            String rutaImagen = String.format("/images/letras/letra%s.jpg", letra);
            imageViews[i].setImage(new Image(getClass().getResourceAsStream(rutaImagen)));
        }

        // Obtener el nivel del jugador
        String nivelStr = CRUDFirebase.obtenerDato(App.db, App.player.getEmail(), "level");
        int nivel;
        try {
            nivel = Integer.parseInt(nivelStr);
        } catch (NumberFormatException e) {

            nivel = 1;
        }

        // Determinar cuántas letras mostrar
        int letrasParaMostrar = (nivel < 8) ? nivel + 2 : 26; // Asumiendo niveles 1 a 7 muestran 3 letras, nivel 8 muestra todas

        // Mostrar/Ocultar imágenes en función del nivel
        for (int i = 0; i < imageViews.length; i++) {
            if (i < letrasParaMostrar) {
                imageViews[i].setVisible(true);
            } else {
                imageViews[i].setVisible(false);
            }
        }
    }

    public void atras() throws IOException {
        App.setRoot("inicio");
    }

}
