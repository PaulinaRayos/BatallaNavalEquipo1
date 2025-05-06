/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import estados.EstadoEstadisticas;
import java.util.Map;

/**
 *
 * @author pauli
 */
public class ComandoOponenteQuiereVolverAJugar implements IComando {

    private EstadoEstadisticas estado;

    public ComandoOponenteQuiereVolverAJugar(EstadoEstadisticas estado) {
        this.estado = estado;
    }
    
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.manejarOponenteQuiereVolverAJugar(mensaje);
    }
    
}
