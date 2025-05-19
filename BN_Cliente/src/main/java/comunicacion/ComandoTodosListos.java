/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import estados.EstadoSalaEspera;
import java.util.Map;

/**
 * Comando que se encarga de manejar la señal cuando todos los jugadores están listos
 * en la sala de espera, permitiendo avanzar al siguiente estado o acción.
 * 
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ComandoTodosListos implements IComando {
    /**
     * Estado de la sala de espera que refleja la situación actual de los jugadores.
     */
    private EstadoSalaEspera estado;

    /**
     * Constructor que inicializa el comando con el estado de la sala de espera dado.
     *
     * @param estado el estado de la sala de espera donde todos los jugadores están listos
     */
    public ComandoTodosListos(EstadoSalaEspera estado) {
        this.estado = estado;
    }
    
    /**
     * Ejecuta el comando para manejar la transición cuando todos los jugadores están listos.
     *
     * @param mensaje un mapa con información adicional (no utilizada en este comando)
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleTodosListos();
    }
}
