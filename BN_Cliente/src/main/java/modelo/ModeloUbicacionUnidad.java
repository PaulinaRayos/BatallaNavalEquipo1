/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Set;

/**
 * Modelo que representa la ubicación de una unidad (nave) en el tablero,
 * incluyendo la unidad y las casillas que ocupa.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ModeloUbicacionUnidad {

    /**
     * Unidad del modelo que se está ubicando.
     */
    private ModeloUnidad unidad;

    /**
     * Conjunto de casillas ocupadas por la unidad.
     */
    private Set<ModeloCasilla> casillasOcupadas;

    /**
     * Constructor que inicializa la unidad y sus casillas ocupadas.
     *
     * @param unidad la unidad del modelo
     * @param casillasOcupadas el conjunto de casillas ocupadas por la unidad
     */
    public ModeloUbicacionUnidad(ModeloUnidad unidad, Set<ModeloCasilla> casillasOcupadas) {
        this.unidad = unidad;
        this.casillasOcupadas = casillasOcupadas;
    }

    /**
     * Obtiene la unidad del modelo.
     *
     * @return la unidad del modelo
     */
    public ModeloUnidad getUnidad() {
        return unidad;
    }

    /**
     * Establece la unidad del modelo.
     *
     * @param unidad la unidad del modelo
     */
    public void setUnidad(ModeloUnidad unidad) {
        this.unidad = unidad;
    }

    /**
     * Obtiene el conjunto de casillas ocupadas por la unidad.
     *
     * @return el conjunto de casillas ocupadas
     */
    public Set<ModeloCasilla> getCasillasOcupadas() {
        return casillasOcupadas;
    }

    /**
     * Establece el conjunto de casillas ocupadas por la unidad.
     *
     * @param casillasOcupadas el conjunto de casillas ocupadas
     */
    public void setCasillasOcupadas(Set<ModeloCasilla> casillasOcupadas) {
        this.casillasOcupadas = casillasOcupadas;
    }

}
