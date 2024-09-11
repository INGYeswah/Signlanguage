package com.mimic.mimic;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;

public class IntruccionesController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void siguiente() {
        try {
            App.setRoot("inicio");
        } catch (IOException ex) {
            Logger.getLogger(IntruccionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
