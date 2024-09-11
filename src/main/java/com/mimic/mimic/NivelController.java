package com.mimic.mimic;

import Model.CRUDFirebase;
import static com.mimic.mimic.NivelesController.letrasRango;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class NivelController implements Initializable {

    String[] letras = new String[]{
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    String lastLetter = "";

    public static int loss = 0;

    private Timeline checkTimeline;

    private StackPane currentStackPane;
    private String currentLetter;

    @FXML
    private Label nickname;

    @FXML
    private Circle circImgInicio;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private VBox lienzo;

    @FXML
    private ImageView im1;

    @FXML
    private ImageView im2;

    @FXML
    private ImageView im3;

    @FXML
    private Label errors;

    @FXML
    private Button btnNext;

    int levelLetras = 0;

    private String[] letrasNivel;

    public static Set<String> letrasError;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        int levelin = Integer.parseInt(CRUDFirebase.obtenerDato(App.db, App.player.getEmail(), "level"));

        if (levelin == 9) {
            btnNext.setDisable(true);
        } else {
            btnNext.setDisable(false);
        }

        letrasError = new HashSet<>();
        letrasNivel = NivelesController.letrasRango;
        CRUDFirebase.obtenerDatos(App.db, App.player.getEmail());
        nickname.setText(App.player.getNickname());
        Image completeIcon = new Image(getClass().getResourceAsStream("/images\\perfil" + App.player.getFoto() + ".png"));
        circImgInicio.setFill(new ImagePattern(completeIcon));
        im1.setImage(null);
        im2.setImage(null);
        im3.setImage(null);
        loss = 0;

        if (App.levelOn == 9) {
            levelLetras = 26;
        } else {
            levelLetras = App.levelOn * 3;
        }
    }

    private void startProgressBar() {
        progressBar.setProgress(0);
        progressBar.setPrefWidth(300);

        Timeline progressTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new javafx.animation.KeyValue(progressBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(20), new javafx.animation.KeyValue(progressBar.progressProperty(), 1))
        );
        progressTimeline.setCycleCount(1);
        progressTimeline.setOnFinished(event -> {
            if (checkTimeline != null) {
                checkTimeline.stop();
                lienzo.getChildren().clear();
            }

            if (loss == 0) {
                im1.setImage(new Image(getClass().getResourceAsStream("/images\\estrella.png")));
                im2.setImage(new Image(getClass().getResourceAsStream("/images\\estrella.png")));
                im3.setImage(new Image(getClass().getResourceAsStream("/images\\estrella.png")));
                int levelin = Integer.parseInt(CRUDFirebase.obtenerDato(App.db, App.player.getEmail(), "level"));
                System.out.println(levelin);
                App.player.setLevel((App.levelOn < 9) ? App.levelOn + 1 : App.levelOn);
                if (!(levelin > App.levelOn)) {
                    CRUDFirebase.modificarDato(App.db, App.player.getEmail(), "level", App.levelOn + 1);
                } else {
                    App.player.setLevel(Integer.parseInt(CRUDFirebase.obtenerDato(App.db, App.player.getEmail(), "level")));
                    App.levelOn = Integer.parseInt(CRUDFirebase.obtenerDato(App.db, App.player.getEmail(), "level"));
                }
            } else if (loss > 0 && loss < 2) {
                im1.setImage(new Image(getClass().getResourceAsStream("/images\\estrella.png")));
                im2.setImage(new Image(getClass().getResourceAsStream("/images\\estrella.png")));
                int levelin = Integer.parseInt(CRUDFirebase.obtenerDato(App.db, App.player.getEmail(), "level"));
                App.player.setLevel((App.levelOn < 9) ? App.levelOn + 1 : App.levelOn);
                if (!(levelin > App.levelOn)) {
                    CRUDFirebase.modificarDato(App.db, App.player.getEmail(), "level", App.levelOn + 1);
                } else {
                    App.player.setLevel(Integer.parseInt(CRUDFirebase.obtenerDato(App.db, App.player.getEmail(), "level")));
                    App.levelOn = Integer.parseInt(CRUDFirebase.obtenerDato(App.db, App.player.getEmail(), "level"));
                }
            } else if (loss >= 2 && loss < 4) {
                im1.setImage(new Image(getClass().getResourceAsStream("/images\\estrella.png")));
            } else if (loss > 4) {
                im1.setImage(new Image(getClass().getResourceAsStream("/images\\estrella.png")));
            }
        });
        progressTimeline.play();
    }

    private void handleStackPaneClick(StackPane stackPane) {
        lienzo.getChildren().remove(stackPane);
        currentStackPane = null; // Reset currentStackPane when it is clicked
    }

    private void checkLetter() {
        String letraActual = retornarLetra();

        if (letraActual.equals(currentLetter) && currentStackPane != null && lienzo.getChildren().contains(currentStackPane)) {
            handleStackPaneClick(currentStackPane);
        } else if (currentStackPane == null || !lienzo.getChildren().contains(currentStackPane)) {
            // Crear el siguiente círculo si no hay ninguno visible o el anterior ha desaparecido
            createAndAddCircle();
        }
    }

    private Circle createCircle(double radius, String color) {
        Circle circle = new Circle(radius);
        circle.setStyle("-fx-fill: " + color + ";");
        return circle;
    }

    private void createShrinkAnimation(Circle originalCircle, Circle borderCircle, StackPane stackPane) {
        Timeline shrinkTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new javafx.animation.KeyValue(borderCircle.radiusProperty(), borderCircle.getRadius())),
                new KeyFrame(Duration.seconds(5), new javafx.animation.KeyValue(borderCircle.radiusProperty(), originalCircle.getRadius()))
        );
        shrinkTimeline.setCycleCount(1);

        shrinkTimeline.setOnFinished(event -> {
            if (lienzo.getChildren().contains(stackPane)) {
                loss++;
                letrasError.add(lastLetter);
                errors.setText("Errores: " + loss);
                System.out.println("Error " + loss);
                lienzo.getChildren().remove(stackPane);
                currentStackPane = null; // Reset currentStackPane after removal
            }
        });

        shrinkTimeline.play();
    }

    private void createAndAddCircle() {
        Random random = new Random();

        Circle originalCircle = createCircle(30, "red");
        Circle borderCircle = createCircle(50, "transparent");
        borderCircle.setStroke(new Color(0, 0, 0, 1));
        borderCircle.setStrokeWidth(3);

        int indiceAleatorio = random.nextInt(letrasNivel.length);
        currentLetter = letrasNivel[indiceAleatorio];
        while (currentLetter.equals(lastLetter)) {
            indiceAleatorio = random.nextInt(letrasNivel.length);
            currentLetter = letrasNivel[indiceAleatorio];
        }
        lastLetter = currentLetter;

        Text text = new Text(currentLetter);
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font-size: 18;");
        text.setTextAlignment(TextAlignment.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(100, 100);
        stackPane.getChildren().addAll(originalCircle, borderCircle, text);

        currentStackPane = stackPane;

        lienzo.getChildren().add(stackPane);

        createShrinkAnimation(originalCircle, borderCircle, stackPane);
    }

    private void startCheckLetter() {

        checkTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> checkLetter()));
        checkTimeline.setCycleCount(Timeline.INDEFINITE);
        checkTimeline.play();

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
            Logger.getLogger(OsuController.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(OsuController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return letra;
    }

    public void iniciar() {
        letrasError = new HashSet<>();
        im1.setImage(null);
        im2.setImage(null);
        im3.setImage(null);
        loss = 0;
        errors.setText("Errores: " + loss);
        startProgressBar();
        startCheckLetter();
    }

    public void atras() throws IOException {
        if (checkTimeline != null) {
            checkTimeline.stop();
            lienzo.getChildren().clear();
            App.setRoot("niveles");
        }
        lienzo.getChildren().clear();
        App.setRoot("niveles");
    }

    public void Errores() throws IOException {
        if (letrasError == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alerta");
            alert.setHeaderText(null);
            alert.setContentText("No tienes errores");
            alert.showAndWait();
            return;
        }

        if (letrasError.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alerta");
            alert.setHeaderText(null);
            alert.setContentText("No tienes errores");
            alert.showAndWait();
            return;
        } else {

            for (String x : letrasError) {
                System.out.println(x);
            }

            App.setRoot("errorLetra");
        }
    }

    public void Next() throws IOException {

        int levelin = Integer.parseInt(CRUDFirebase.obtenerDato(App.db, App.player.getEmail(), "level"));

        if (levelin == 9) {
            btnNext.setDisable(true);
        } else {
            btnNext.setDisable(false);
        }

        System.out.println(App.levelOn);
        if (levelin > App.nivelActual) {
            App.nivelActual += 1;
            App.levelOn += 1;
            System.out.println(App.nivelActual);
            System.out.println(App.levelOn);

            if (App.nivelActual == 9) {
                letrasRango = Arrays.copyOfRange(letras, 3 * (App.levelOn - 1), (App.levelOn * 3) - 1);
            } else {
                letrasRango = Arrays.copyOfRange(letras, 3 * (App.levelOn - 1), (App.levelOn * 3));
            }
            App.setRoot("introLetra");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alerta");
            alert.setHeaderText(null);
            alert.setContentText("No puedes pasar el nivel hasta que tengas dos o mas estrellas");
            alert.showAndWait();
            return;
        }
    }
}
