/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa el tablero del juego, incluyendo sus casillas, unidades
 * colocadas, disparos recibidos y el número de naves destruidas.
 *
 * El tablero es de tamaño fijo 10x10.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ModeloTablero {

    /**
     * Número de filas del tablero.
     */
    private final int FILAS = 10;

    /**
     * Número de columnas del tablero.
     */
    private final int COLUMNAS = 10;

    /**
     * Matriz de casillas que conforman el tablero.
     */
    private ModeloCasilla[][] casillas;

    /**
     * Lista de unidades colocadas en el tablero.
     */
    private List<ModeloUbicacionUnidad> unidades;

    /**
     * Lista de disparos que ha recibido el tablero.
     */
    private List<ModeloDisparo> disparosRecibidos;

    /**
     * Contador de naves destruidas.
     */
    private int numNavesDestruidas;

    /**
     * Constructor por defecto que inicializa el tablero con casillas, unidades
     * y disparos.
     */
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

    /**
     * Agrega una unidad al tablero.
     *
     * @param ubicacion la ubicación de la unidad a agregar
     */
    public void agregarUbicacion(ModeloUbicacionUnidad ubicacion) {
        this.unidades.add(ubicacion);
    }

    /**
     * Limpia el tablero, eliminando unidades, disparos y reiniciando el
     * contador de naves destruidas.
     */
    public void limpiarTablero() {
        this.unidades.forEach((ubicacion) -> {
            ubicacion.limipiarCasillas();
        });
        this.disparosRecibidos.clear();
        this.numNavesDestruidas = 0;
    }

    /**
     * Agrega un disparo recibido al tablero.
     *
     * @param disparo el disparo recibido
     */
    public void addDisparoRecibido(ModeloDisparo disparo) {
        this.disparosRecibidos.add(disparo);
    }

    /**
     * Devuelve la lista de unidades colocadas en el tablero.
     *
     * @return lista de ubicaciones de unidades
     */
    public List<ModeloUbicacionUnidad> getUnidades() {
        return unidades;
    }

    /**
     * Constructor alternativo que permite establecer el número de naves
     * destruidas.
     *
     * @param numNavesDestruidas número inicial de naves destruidas
     */
    public ModeloTablero(int numNavesDestruidas) {
        this.numNavesDestruidas = numNavesDestruidas;
    }

    /**
     * Devuelve el número de naves destruidas.
     *
     * @return número de naves destruidas
     */
    public int getNumNavesDestruidas() {
        return numNavesDestruidas;
    }

    /**
     * Establece el número de naves destruidas.
     *
     * @param numNavesDestruidas nuevo valor para el contador de naves
     * destruidas
     */
    public void setNumNavesDestruidas(int numNavesDestruidas) {
        this.numNavesDestruidas = numNavesDestruidas;
    }

    /**
     * Incrementa en uno el número de naves destruidas.
     */
    public void addNaveDestruida() {
        this.numNavesDestruidas += 1;
    }

    /**
     * Devuelve la lista de disparos que ha recibido el tablero.
     *
     * @return lista de disparos recibidos
     */
    public List<ModeloDisparo> getDisparosRecibidos() {
        return disparosRecibidos;
    }

    /**
     * Establece una nueva lista de disparos recibidos.
     *
     * @param disparosRecibidos nueva lista de disparos
     */
    public void setDisparosRecibidos(List<ModeloDisparo> disparosRecibidos) {
        this.disparosRecibidos = disparosRecibidos;
    }

}
