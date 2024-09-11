package com.mimic.mimic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Puntuacion {
    private final SimpleStringProperty nombre;
    private final SimpleIntegerProperty puntuacion;

    public Puntuacion(String nombre, int puntuacion) {
        this.nombre = new SimpleStringProperty(nombre);
        this.puntuacion = new SimpleIntegerProperty(puntuacion);
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public int getPuntuacion() {
        return puntuacion.get();
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion.set(puntuacion);
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public SimpleIntegerProperty puntuacionProperty() {
        return puntuacion;
    }
}
