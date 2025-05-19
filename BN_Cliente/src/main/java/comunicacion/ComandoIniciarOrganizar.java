/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import estados.EstadoSalaEspera;
import java.util.Map;

/**
 * Comando que indica el inicio de la fase de organización de barcos
 * desde la sala de espera, una vez que los jugadores están listos.
 * 
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ComandoIniciarOrganizar implements IComando {

    /**
     * Estado de la sala de espera, responsable de cambiar a la fase de organización.
     */
    private EstadoSalaEspera estado;

    /**
     * Crea una instancia del comando asociada al estado de la sala de espera.
     *
     * @param estado instancia del estado desde el cual se iniciará la organización
     */
    public ComandoIniciarOrganizar(EstadoSalaEspera estado) {
        this.estado = estado;
    }

    /**
     * Ejecuta el comando que da paso a la fase de organización.
     * Este método no requiere datos adicionales del mensaje.
     *
     * @param mensaje mapa con información del mensaje recibido (no utilizado)
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleIniciarOrganizar();
    }
}