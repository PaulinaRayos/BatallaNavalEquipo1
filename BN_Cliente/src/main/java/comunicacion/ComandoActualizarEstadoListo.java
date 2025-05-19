/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import estados.EstadoSalaEspera;
import java.util.Map;

/**
 * Comando que permite notificar y manejar la actualización del estado "listo"
 * de un jugador dentro de la sala de espera.
 *
 * Este comando delega la acción al estado actual de la sala de espera.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ComandoActualizarEstadoListo implements IComando {

    /**
     * Referencia al estado actual de la sala de espera que manejará la
     * actualización.
     */
    private EstadoSalaEspera estado;

    /**
     * Crea un nuevo comando con el estado de sala de espera al que se delegarán
     * las acciones.
     *
     * @param estado instancia del estado de sala de espera que recibirá la
     * actualización
     */
    public ComandoActualizarEstadoListo(EstadoSalaEspera estado) {
        this.estado = estado;
    }

    /**
     * Ejecuta el comando para procesar la actualización del estado "listo" de
     * un jugador.
     *
     * @param mensaje mapa con los datos necesarios para realizar la
     * actualización, típicamente contiene identificadores y banderas de estado
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleActualizarEstadoListo(mensaje);
    }
}
