package com.mimic.mimic;

import java.util.ArrayList;

public class Result<T1, T2> {
    private ArrayList<T1> lista1;
    private ArrayList<T2> lista2;

    public Result(ArrayList<T1> lista1, ArrayList<T2> lista2) {
        this.lista1 = lista1;
        this.lista2 = lista2;
    }

    public ArrayList<T1> getLista1() {
        return lista1;
    }

    public ArrayList<T2> getLista2() {
        return lista2;
    }
}
