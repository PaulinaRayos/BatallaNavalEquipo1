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
public class ComandoTodosListos implements IComando{
    /**
     * Estado de la sala de espera donde todos los jugadores están listos.
     */
    private EstadoSalaEspera estado;

    /**
     * Constructor que inicializa el comando con el estado de la sala de espera especificado.
     *
     * @param estado el estado de la sala de espera donde todos los jugadores están listos
     */
    public ComandoTodosListos(EstadoSalaEspera estado) {
        this.estado = estado;
    }
    
    /**
     * Ejecuta el comando para manejar el estado cuando todos los jugadores están listos.
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleTodosListos();
    }
}
