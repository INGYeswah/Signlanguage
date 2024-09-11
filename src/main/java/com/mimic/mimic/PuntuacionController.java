package com.mimic.mimic;

import Model.CRUDFirebase;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PuntuacionController implements Initializable {

    @FXML
    private Label lb1;
    
    @FXML
    private TableColumn<Puntuacion, String> nombreColumna;
    @FXML
    private TableColumn<Puntuacion, Integer> puntuacionColumna;
    @FXML
    private TableView<Puntuacion> tablaPuntuacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        lb1.setText("Tu puntuacion maxima: " + CRUDFirebase.obtenerDato(App.db, App.player.getEmail(), "puntuacion"));
        
        // Inicializar las columnas de la tabla
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        puntuacionColumna.setCellValueFactory(new PropertyValueFactory<>("puntuacion"));

        // Obtener datos
        Result resultado = CRUDFirebase.obtenerDatosPuntuaciones(App.db);
        ArrayList<String> nombres = resultado.getLista1();
        ArrayList<Integer> puntuaciones = resultado.getLista2();

        // Crear una lista de objetos Puntuacion
        List<Puntuacion> listaPuntuaciones = new ArrayList<>();
        for (int i = 0; i < puntuaciones.size(); i++) {
            listaPuntuaciones.add(new Puntuacion(nombres.get(i), puntuaciones.get(i)));
        }

        // Ordenar por puntuacion en orden descendente y tomar las 17 mejores
        List<Puntuacion> mejores17 = listaPuntuaciones.stream()
                .sorted(Comparator.comparingInt(Puntuacion::getPuntuacion).reversed())
                .limit(17)
                .collect(Collectors.toList());

        // Convertir a ObservableList
        ObservableList<Puntuacion> datos = FXCollections.observableArrayList(mejores17);

        // Establecer los datos en la tabla
        tablaPuntuacion.setItems(datos);
    }

    public void atras() throws IOException {
        App.setRoot("gameOver");
    }

}
