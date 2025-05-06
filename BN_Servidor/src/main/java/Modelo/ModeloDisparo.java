/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import enums.EstadoCasilla;

/**
 *
 * @author pauli
 */
public class ModeloDisparo {
    private ModeloCasilla objetivo;
    private EstadoCasilla estado;

    public ModeloDisparo(ModeloCasilla objetivo, EstadoCasilla estado) {
        this.objetivo = objetivo;
        this.estado = estado;
    }
    
    public ModeloCasilla getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(ModeloCasilla objetivo) {
        this.objetivo = objetivo;
    }

    public EstadoCasilla getEstado() {
        return estado;
    }

    public void setEstado(EstadoCasilla estado) {
        this.estado = estado;
    }

}
