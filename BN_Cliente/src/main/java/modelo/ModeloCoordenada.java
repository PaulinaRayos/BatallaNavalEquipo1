/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author pauli
 */
public class ModeloCoordenada {
    /**
     * Coordenada x en el tablero.
     */
    private int x;
    
    /**
     * Coordenada y en el tablero.
     */
    private int y;

    /**
     * Constructor que inicializa las coordenadas x e y.
     *
     * @param x la coordenada x
     * @param y la coordenada y
     */
    public ModeloCoordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Obtiene la coordenada x.
     *
     * @return la coordenada x
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la coordenada y.
     *
     * @return la coordenada y
     */
    public int getY() {
        return y;
    }
    
}
