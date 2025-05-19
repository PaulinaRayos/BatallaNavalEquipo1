package Modelo;

import enums.EstadoCasilla;

/**
 * Representa un disparo realizado durante la partida. Contiene la casilla
 * objetivo del disparo y el estado de la misma después del disparo.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ModeloDisparo {

    /**
     * Casilla objetivo a la que fue dirigido el disparo.
     */
    private ModeloCasilla objetivo;

    /**
     * Estado de la casilla después de recibir el disparo. Puede representar si
     * fue un impacto, fallo, o si ya estaba dañada.
     */
    private EstadoCasilla estado;

    /**
     * Constructor que permite inicializar un disparo con su objetivo y estado
     * resultante.
     *
     * @param objetivo la casilla objetivo del disparo
     * @param estado el estado de la casilla tras el disparo
     */
    public ModeloDisparo(ModeloCasilla objetivo, EstadoCasilla estado) {
        this.objetivo = objetivo;
        this.estado = estado;
    }

    /**
     * Devuelve la casilla objetivo del disparo.
     *
     * @return la casilla objetivo
     */
    public ModeloCasilla getObjetivo() {
        return objetivo;
    }

    /**
     * Establece la casilla objetivo del disparo.
     *
     * @param objetivo nueva casilla objetivo
     */
    public void setObjetivo(ModeloCasilla objetivo) {
        this.objetivo = objetivo;
    }

    /**
     * Devuelve el estado de la casilla después del disparo.
     *
     * @return el estado de la casilla
     */
    public EstadoCasilla getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la casilla después del disparo.
     *
     * @param estado nuevo estado de la casilla
     */
    public void setEstado(EstadoCasilla estado) {
        this.estado = estado;
    }

}
