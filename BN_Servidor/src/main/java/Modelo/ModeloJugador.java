/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.List;

/**
 * Representa a un jugador dentro de la partida. Contiene su nombre,
 * identificador, estadísticas, unidades, y estados como "listo" o "quiere
 * revancha".
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ModeloJugador {

    /**
     * Nombre del jugador.
     */
    private String nombre;

    /**
     * Estadísticas asociadas al jugador.
     */
    private ModeloEstadisticas estadisticas;

    /**
     * Lista de unidades del jugador.
     */
    private List<ModeloUnidad> unidades;

    /**
     * Identificador único del jugador.
     */
    private String id;

    /**
     * Indica si el jugador está listo para comenzar la partida.
     */
    private boolean listo;

    /**
     * Indica si el jugador desea jugar una revancha.
     */
    private boolean quiereRevancha;

    /**
     * Constructor que inicializa nombre, estadísticas y unidades.
     *
     * @param nombre nombre del jugador
     * @param estadisticas estadísticas del jugador
     * @param unidades lista de unidades del jugador
     */
    public ModeloJugador(String nombre, ModeloEstadisticas estadisticas, List<ModeloUnidad> unidades) {
        this.nombre = nombre;
        this.estadisticas = estadisticas;
        this.unidades = unidades;
        this.listo = false;
        this.quiereRevancha = false;
    }

    /**
     * Constructor que inicializa nombre y estadísticas.
     *
     * @param nombre nombre del jugador
     * @param estadisticas estadísticas del jugador
     */
    public ModeloJugador(String nombre, ModeloEstadisticas estadisticas) {
        this.nombre = nombre;
        this.estadisticas = estadisticas;
    }

    /**
     * Constructor vacío.
     */
    public ModeloJugador() {
    }

    /**
     * Constructor que inicializa ID y nombre del jugador.
     *
     * @param id identificador único del jugador
     * @param nombre nombre del jugador
     */
    public ModeloJugador(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Devuelve el nombre del jugador.
     *
     * @return nombre del jugador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del jugador.
     *
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve las estadísticas del jugador.
     *
     * @return objeto de estadísticas
     */
    public ModeloEstadisticas getEstadisticas() {
        return estadisticas;
    }

    /**
     * Establece las estadísticas del jugador.
     *
     * @param estadisticas nuevas estadísticas
     */
    public void setEstadisticas(ModeloEstadisticas estadisticas) {
        this.estadisticas = estadisticas;
    }

    /**
     * Devuelve el identificador del jugador.
     *
     * @return ID del jugador
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador del jugador.
     *
     * @param id nuevo ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Devuelve la lista de unidades del jugador.
     *
     * @return lista de unidades
     */
    public List<ModeloUnidad> getUnidades() {
        return unidades;
    }

    /**
     * Establece la lista de unidades del jugador.
     *
     * @param unidades nueva lista de unidades
     */
    public void setUnidades(List<ModeloUnidad> unidades) {
        this.unidades = unidades;
    }

    /**
     * Agrega una unidad a la lista de unidades del jugador.
     *
     * @param unidad unidad a agregar
     */
    public void addUnidad(ModeloUnidad unidad) {
        this.unidades.add(unidad);
    }

    /**
     * Indica si el jugador está listo para la partida.
     *
     * @return true si está listo, false si no
     */
    public boolean isListo() {
        return listo;
    }

    /**
     * Establece el estado de preparación del jugador.
     *
     * @param listo true si está listo, false si no
     */
    public void setListo(boolean listo) {
        this.listo = listo;
    }

    /**
     * Indica si el jugador desea jugar una revancha.
     *
     * @return true si quiere revancha, false si no
     */
    public boolean isQuiereRevancha() {
        return quiereRevancha;
    }

    /**
     * Establece si el jugador desea una revancha.
     *
     * @param quiereRevancha true si quiere revancha, false si no
     */
    public void setQuiereRevancha(boolean quiereRevancha) {
        this.quiereRevancha = quiereRevancha;
    }
}
