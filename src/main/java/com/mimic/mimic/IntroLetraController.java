package com.mimic.mimic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class IntroLetraController implements Initializable {

    int letraNow = 0;

    String lastLetter = "";

    @FXML
    private VBox lienzo;

    @FXML
    private ImageView im;

    @FXML
    private Label learnLetter;

    @FXML
    private Label solicitud;

    private StackPane currentStackPane;
    private String currentLetter;

    private String[] letrasNivel;

    private Timeline checkTimeline;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        letrasNivel = NivelesController.letrasRango;
        for (String x : letrasNivel) {
            System.out.println(x);
        }
        solicitud.setText("Por favor has la letra " + letrasNivel[letraNow] + " para continuar");
        learnLetter.setText("Esta es la letra " + letrasNivel[letraNow]);
        im.setImage(new Image(getClass().getResourceAsStream("/images\\letras\\letra" + letrasNivel[letraNow] + ".jpg")));
        startCheckLetter();
    }

    private void startCheckLetter() {
        checkTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            try {
                checkLetter();
            } catch (IOException ex) {
                Logger.getLogger(IntroLetraController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }));
        checkTimeline.setCycleCount(Timeline.INDEFINITE);
        checkTimeline.play();
    }

    private void checkLetter() throws IOException {
        String letraActual = retornarLetra();

        if (letraActual.equals(currentLetter) && currentStackPane != null && lienzo.getChildren().contains(currentStackPane)) {
            handleStackPaneClick(currentStackPane);
        } else if (currentStackPane == null || !lienzo.getChildren().contains(currentStackPane)) {

            if (letraNow + 1 <= letrasNivel.length) {
                learnLetter.setText("Esta es la letra " + letrasNivel[letraNow]);
                solicitud.setText("Por favor has la letra " + letrasNivel[letraNow] + " para continuar");
                im.setImage(new Image(getClass().getResourceAsStream("/images\\letras\\letra" + letrasNivel[letraNow] + ".jpg")));
                // Crear el siguiente círculo si no hay ninguno visible o el anterior ha desaparecido
                createAndAddCircle();
            } else {
                App.setRoot("nivel");
                checkTimeline.stop();
            }

        }
    }

    private void createAndAddCircle() {
        Random random = new Random();

        Circle originalCircle = createCircle(30, "red");

        currentLetter = letrasNivel[letraNow];

        Text text = new Text(currentLetter);
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font-size: 18;");
        text.setTextAlignment(TextAlignment.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(100, 100);
        stackPane.getChildren().addAll(originalCircle, text);

        currentStackPane = stackPane;

        lienzo.getChildren().add(stackPane);
        letraNow++;
    }

    private Circle createCircle(double radius, String color) {
        Circle circle = new Circle(radius);
        circle.setCenterX(radius);
        circle.setCenterY(radius);
        circle.setStyle("-fx-fill: " + color + ";");
        return circle;
    }

    private String retornarLetra() {
        String letra = "";
        try {
            String endpointUrl = "http://localhost:8080/api/submit";

            URL url = new URL(endpointUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            br.close();
            letra = response.toString();
            return letra;
        } catch (MalformedURLException ex) {
            Logger.getLogger(OsuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OsuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return letra;
    }

    private void handleStackPaneClick(StackPane stackPane) {
        lienzo.getChildren().remove(stackPane);
        currentStackPane = null; // Reset currentStackPane when it is clicked
    }

}
