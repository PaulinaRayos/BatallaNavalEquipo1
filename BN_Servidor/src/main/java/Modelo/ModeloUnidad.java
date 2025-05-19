package Modelo;

import enums.EstadoUnidad;
import enums.Orientacion;

/**
 * Representa una unidad (nave) del juego con sus propiedades como nombre,
 * tamaño, vida, orientación y estado.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ModeloUnidad {

    private int numNave;
    private final String nombre;
    private final int tamanio;
    private int vida;
    private int vidaMax;
    private Orientacion orientacion;
    private EstadoUnidad estado;

    /**
     * Crea una nueva unidad con el nombre y tamaño especificados.
     *
     * @param nombre el nombre de la unidad
     * @param tamanio el tamaño de la unidad (también determina la vida inicial)
     */
    public ModeloUnidad(String nombre, int tamanio) {
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.vida = tamanio;
        this.vidaMax = vida;
        this.estado = estado.INTACTA;
    }

    /**
     * Restaura la vida actual al valor máximo.
     */
    public void resetearVida() {
        this.vida = vidaMax;
    }

    /**
     * Obtiene el número identificador de la nave.
     *
     * @return el número de la nave
     */
    public int getNumNave() {
        return numNave;
    }

    /**
     * Establece el número identificador de la nave.
     *
     * @param numNave el número de la nave
     */
    public void setNumNave(int numNave) {
        this.numNave = numNave;
    }

    /**
     * Obtiene la vida actual de la unidad.
     *
     * @return la vida actual
     */
    public int getVida() {
        return vida;
    }

    /**
     * Establece la vida actual de la unidad.
     *
     * @param vida la nueva cantidad de vida
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Obtiene la vida máxima de la unidad.
     *
     * @return la vida máxima
     */
    public int getVidaMax() {
        return vidaMax;
    }

    /**
     * Establece la vida máxima de la unidad.
     *
     * @param vidaMax la nueva vida máxima
     */
    public void setVidaMax(int vidaMax) {
        this.vidaMax = vidaMax;
    }

    /**
     * Obtiene la orientación de la unidad.
     *
     * @return la orientación actual
     */
    public Orientacion getOrientacion() {
        return orientacion;
    }

    /**
     * Establece la orientación de la unidad.
     *
     * @param orientacion la nueva orientación
     */
    public void setOrientacion(Orientacion orientacion) {
        this.orientacion = orientacion;
    }

    /**
     * Obtiene el estado actual de la unidad.
     *
     * @return el estado actual
     */
    public EstadoUnidad getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la unidad.
     *
     * @param estado el nuevo estado de la unidad
     */
    public void setEstado(EstadoUnidad estado) {
        this.estado = estado;
        this.estado = EstadoUnidad.INTACTA;
    }

    /**
     * Obtiene el tamaño de la unidad.
     *
     * @return el tamaño de la unidad
     */
    public int getTamanio() {
        return tamanio;
    }

    /**
     * Reinicia la vida de la unidad a su valor máximo y la marca como intacta.
     */
    public void reiniciarVida() {
        this.vida = vidaMax;
        this.estado = EstadoUnidad.INTACTA;
    }

    /**
     * Devuelve una representación en cadena de la unidad.
     *
     * @return una cadena con el número de nave y el nombre
     */
    @Override
    public String toString() {
        return "ModeloUnidad{" + "numNave=" + numNave + ", nombre=" + nombre + '}';
    }

}
