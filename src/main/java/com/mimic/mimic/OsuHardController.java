package com.mimic.mimic;

import Model.CRUDFirebase;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

public class OsuHardController implements Initializable {

    String[] letras = new String[]{
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    String lastLetter = "";

    @FXML
    private AnchorPane lienzo;

    private StackPane currentStackPane;
    private String currentLetter;
    private boolean isMouseOverCurrentStackPane = false; // Track if mouse is over the current StackPane

    private Timeline checkTimeline;

    public static int puntuacion;

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

    private void startCheckLetter() {
        checkTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> checkLetter()));
        checkTimeline.setCycleCount(Timeline.INDEFINITE);
        checkTimeline.play();
    }

    private void checkLetter() {
        String letraActual = retornarLetra();

        if (letraActual.equals(currentLetter) && isMouseOverCurrentStackPane && currentStackPane != null && lienzo.getChildren().contains(currentStackPane)) {
            handleStackPaneClick(currentStackPane);
        } else if (currentStackPane == null || !lienzo.getChildren().contains(currentStackPane)) {
            createAndAddCircle();
        }
    }

    private void createAndAddCircle() {
        Random random = new Random();

        int width = random.nextInt((540 - 60 + 1)) + 60;
        int height = random.nextInt((500 - 60 + 1)) + 60;

        Circle originalCircle = createCircle(30, width, height, "red");
        Circle borderCircle = createCircle(50, width, height, "transparent");
        borderCircle.setStroke(new Color(0, 0, 0, 1));
        borderCircle.setStrokeWidth(3);

        int indiceAleatorio = random.nextInt(26);
        currentLetter = letras[indiceAleatorio];

        while (currentLetter.equals(lastLetter)) {
            indiceAleatorio = random.nextInt(26);
            currentLetter = letras[indiceAleatorio];
        }
        lastLetter = currentLetter;

        Text text = new Text(currentLetter);
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font-size: 18;");
        text.setTextAlignment(TextAlignment.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(100, 100);
        stackPane.setLayoutX(width - 50);
        stackPane.setLayoutY(height - 50);
        stackPane.getChildren().addAll(originalCircle, borderCircle, text);

        currentStackPane = stackPane;

        // Track if mouse is over the current StackPane
        stackPane.setOnMouseEntered(event -> isMouseOverCurrentStackPane = true);
        stackPane.setOnMouseExited(event -> isMouseOverCurrentStackPane = false);

        lienzo.getChildren().add(stackPane);

        createShrinkAnimation(originalCircle, borderCircle, stackPane);
    }

    private void createShrinkAnimation(Circle originalCircle, Circle borderCircle, StackPane stackPane) {
        Timeline shrinkTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new javafx.animation.KeyValue(borderCircle.radiusProperty(), borderCircle.getRadius())),
                new KeyFrame(Duration.seconds(5), new javafx.animation.KeyValue(borderCircle.radiusProperty(), originalCircle.getRadius()))
        );
        shrinkTimeline.setCycleCount(1);

        shrinkTimeline.setOnFinished(event -> {
            if (lienzo.getChildren().contains(stackPane)) {
                lienzo.getChildren().remove(stackPane);
                currentStackPane = null; // Reset currentStackPane after removal
                checkTimeline.stop();
                lienzo.getChildren().clear();
                try {
                    if (puntuacion > Integer.parseInt(CRUDFirebase.obtenerDato(App.db, App.player.getEmail(), "puntuacion"))) {
                        CRUDFirebase.modificarDato(App.db, App.player.getEmail(), "puntuacion", puntuacion);
                    }
                    App.osu = 1;
                    App.setRoot("gameOver");
                } catch (IOException ex) {
                    Logger.getLogger(OsuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        shrinkTimeline.play();
    }

    private Circle createCircle(double radius, double centerX, double centerY, String color) {
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
        isMouseOverCurrentStackPane = false; // Reset mouse over flag
        puntuacion++;
    }

    public void iniciar() {
        startCheckLetter();
    }

    public void Atras() throws IOException {
        if (checkTimeline != null) {
            checkTimeline.stop();
            lienzo.getChildren().clear();
            App.setRoot("osuSelect");
        }
        lienzo.getChildren().clear();
        App.setRoot("osuSelect");
    }
}
