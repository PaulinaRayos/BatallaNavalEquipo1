/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pauli
 */
public class ModeloTablero {

    private final int FILAS = 10;
    private final int COLUMNAS = 10;
    private ModeloCasilla[][] casillas;
    private List<ModeloUbicacionUnidad> unidades;
    private List<ModeloDisparo> disparosRecibidos;
    private int numNavesDestruidas;

    // En el constructor inicial se crea la lista de casillas
    public ModeloTablero() {

        casillas = new ModeloCasilla[FILAS][COLUMNAS];

        // se crean las casillas
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ModeloCasilla celda = new ModeloCasilla(new ModeloCoordenada(i, j));
                casillas[i][j] = celda;
            }
        }

        // se inician la lista de ubicaciones y de los disparos realizados
        this.unidades = new ArrayList<>();
        this.disparosRecibidos = new ArrayList<>();
        this.numNavesDestruidas = 0;
    }

    public void agregarUbicacion(ModeloUbicacionUnidad ubicacion) {
        this.unidades.add(ubicacion);
    }

    public void limpiarTablero() {
        this.unidades.forEach((ubicacion) -> {
            ubicacion.limipiarCasillas();
        });
        this.disparosRecibidos.clear();
        this.numNavesDestruidas = 0;
    }

    public void addDisparoRecibido(ModeloDisparo disparo) {
        this.disparosRecibidos.add(disparo);
    }

    public List<ModeloUbicacionUnidad> getUnidades() {
        return unidades;
    }

    public ModeloTablero(int numNavesDestruidas) {
        this.numNavesDestruidas = numNavesDestruidas;
    }

    public int getNumNavesDestruidas() {
        return numNavesDestruidas;
    }

    public void setNumNavesDestruidas(int numNavesDestruidas) {
        this.numNavesDestruidas = numNavesDestruidas;
    }

    public void addNaveDestruida() {
        this.numNavesDestruidas += 1;
    }

    public List<ModeloDisparo> getDisparosRecibidos() {
        return disparosRecibidos;
    }

    public void setDisparosRecibidos(List<ModeloDisparo> disparosRecibidos) {
        this.disparosRecibidos = disparosRecibidos;
    }

}
