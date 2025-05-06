/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Map;

/**
 *
 * @author pauli
 */
public class ModeloUbicacionUnidad {
    private ModeloUnidad unidad;
    private Map<ModeloCasilla, Boolean> casillas;

    public ModeloUbicacionUnidad(ModeloUnidad unidad, Map<ModeloCasilla, Boolean> casillas) {
        this.unidad = unidad;
        this.casillas = casillas;
    }

    public void addCasilla(ModeloCasilla casilla, Boolean bool) {
        this.casillas.put(casilla, bool);
    }

    public ModeloUnidad getUnidad() {
        return unidad;
    }

    public void setUnidad(ModeloUnidad unidad) {
        this.unidad = unidad;
    }

    public Map<ModeloCasilla, Boolean> getCasillas() {
        return casillas;
    }

    public void limipiarCasillas() {
        this.casillas.clear();
    }

    public void setCasillas(Map<ModeloCasilla, Boolean> casillas) {
        this.casillas = casillas;
    }

    @Override
    public String toString() {
        return "UbicacionUnidad{" + "unidad=" + unidad + ", casillas=" + casillas + '}';
    }

}
