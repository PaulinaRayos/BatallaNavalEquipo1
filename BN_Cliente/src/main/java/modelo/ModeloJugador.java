/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author pauli
 */
public class ModeloJugador {
    
/**
     * Nombre del jugador.
     */
    private String nombre;
    
    /**
     * Identificador del jugador.
     */
    private String id;
    
    /**
     * Constructor privado 
     */
    private ModeloJugador() {
    }
    
    /**
     * Obtiene la instancia única del jugador.
     *
     * @return la instancia única de ModeloJugador
     */
    public static ModeloJugador getInstance() {
        return ModeloJugadorHolder.INSTANCE;
    }
    
    /**
     * Clase interna que mantiene la instancia única del jugador.
     */
    private static class ModeloJugadorHolder {

        /**
         * Instancia única del jugador.
         */
        private static final ModeloJugador INSTANCE = new ModeloJugador();
    }

    /**
     * Obtiene el nombre del jugador.
     *
     * @return el nombre del jugador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del jugador.
     *
     * @param nombre el nombre del jugador
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador del jugador.
     *
     * @return el identificador del jugador
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador del jugador.
     *
     * @param id el identificador del jugador
     */
    public void setId(String id) {
        this.id = id;
    }
    
}
