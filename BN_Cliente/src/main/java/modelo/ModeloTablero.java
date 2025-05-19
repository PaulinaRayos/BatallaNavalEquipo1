/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import tableroStrategy.ITableroObserver;

/**
 * Modelo que representa el tablero del juego, compuesto por una matriz de
 * casillas y un conjunto de unidades ubicadas. Implementa el patrón Observer
 * para notificar cambios a los observadores.
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
     * Matriz de casillas que componen el tablero.
     */
    private ModeloCasilla[][] casillas;

    /**
     * Conjunto de unidades ubicadas en el tablero.
     */
    private Set<ModeloUbicacionUnidad> unidades;

    /**
     * Lista de observadores del tablero.
     */
    private List<ITableroObserver> observers = new ArrayList<>();

    /**
     * Constructor que inicializa el tablero y sus casillas.
     */
    public ModeloTablero() {
        casillas = new ModeloCasilla[FILAS][COLUMNAS];

        // Se crean las casillas
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                casillas[i][j] = new ModeloCasilla(new ModeloCoordenada(i, j));
            }
        }

        // Se inicia el conjunto de unidades
        this.unidades = new HashSet<>();
    }

    /**
     * Obtiene la matriz de casillas del tablero.
     *
     * @return la matriz de casillas del tablero
     */
    public ModeloCasilla[][] getCasillas() {
        return casillas;
    }

    /**
     * Obtiene el conjunto de unidades ubicadas en el tablero.
     *
     * @return el conjunto de unidades en el tablero
     */
    public Set<ModeloUbicacionUnidad> getUnidades() {
        return unidades;
    }

    /**
     * Obtiene una casilla del tablero según sus coordenadas.
     *
     * @param x la coordenada x de la casilla
     * @param y la coordenada y de la casilla
     * @return la casilla correspondiente, o null si está fuera del tablero
     */
    public ModeloCasilla getCasilla(int x, int y) {
        if (x >= 0 && x < FILAS && y >= 0 && y < COLUMNAS) {
            return casillas[x][y];
        }
        return null;
    }

    /**
     * Agrega un observador del tablero.
     *
     * @param observer el observador a agregar
     */
    public void addObserver(ITableroObserver observer) {
        observers.add(observer);
    }

    /**
     * Elimina un observador del tablero.
     *
     * @param observer el observador a eliminar
     */
    public void removeObserver(ITableroObserver observer) {
        observers.remove(observer);
    }

    /**
     * Actualiza el tablero y notifica a los observadores.
     */
    public void actualizarTablero() {
        notifyObservers();
    }

    /**
     * Notifica a todos los observadores que el tablero ha sido actualizado.
     */
    private void notifyObservers() {
        for (ITableroObserver observer : observers) {
            observer.onTableroUpdated();
        }
    }
}
