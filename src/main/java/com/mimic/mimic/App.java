package com.mimic.mimic;

import Model.CRUDFirebase;
import Model.ConexionFirebase;
import Model.Player;
import Soundtrack.Soundtrack;
import com.google.cloud.firestore.Firestore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import org.springframework.boot.SpringApplication;

public class App extends Application {

    static Soundtrack sound;
    private static Scene scene;
    public static Firestore db;
    public static Player player;
    public static int levelOn;
    public static int osu;
    public static int nivelActual;

    @Override
    public void start(Stage stage) throws IOException {

        // -- Scene -- //
        scene = new Scene(loadFXML("login"));

        // -- CSS -- //
        scene.getStylesheets().add(App.class.getResource("primary.css").toExternalForm());

        // -- Add scene -- //
        stage.setScene(scene);

        // -- Scene/Icon -- //
        Image completeIcon = new Image(getClass().getResourceAsStream("/images\\completeIcon.png"));
        stage.getIcons().add(completeIcon);

        // -- Stage/Configuration -- //
        stage.setResizable(false);
        stage.setTitle("Mimic");

        // -- Close Window event -- //
        stage.setOnCloseRequest(event -> {
            // Alert message //
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de Cierre");
            alert.setHeaderText("¿Estás seguro de que deseas cerrar la aplicación?");
            alert.setContentText("El progreso no guardado se perderá.");

            if (alert.showAndWait().get().getButtonData().isCancelButton()) {
                event.consume();
            } else {
                System.exit(0);
            }
        });

        // -- Show -- //
        stage.show();

        // -- Musica -- //
        sound = new Soundtrack();
        sound.start();

        // -- Conexion Firebase -- //
        ConexionFirebase conexion = new ConexionFirebase();
        db = conexion.Conexion();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        SpringApplication.run(VideoProcessingApplication.class, args);
        launch();
    }

}
