/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import estados.EstadoSalaEspera;
import java.util.Map;

/**
 *
 * @author pauli
 */
public class ComandoActualizarEstadoListo implements IComando {
    /**
     * Estado de la sala de espera que se actualizará.
     */
    private EstadoSalaEspera estado;

    /**
     * Constructor que inicializa el comando con el estado de la sala de espera especificado.
     *
     * @param estado el estado de la sala de espera que será actualizado
     */
    public ComandoActualizarEstadoListo(EstadoSalaEspera estado) {
        this.estado = estado;
    }
    
    /**
     * Ejecuta el comando para actualizar el estado de "listo" en la sala de espera.
     *
     * @param mensaje un mapa que contiene los datos necesarios para actualizar el estado
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleActualizarEstadoListo(mensaje);
    }
}
