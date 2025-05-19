/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import estados.EstadoSalaEspera;
import java.util.Map;

/**
 * Comando que gestiona la creación de una nueva partida en el contexto
 * de la sala de espera. La lógica se delega al estado correspondiente.
 * 
 * Este comando es parte del patrón de comandos usado para modularizar
 * las acciones en el sistema de comunicación.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ComandoCrearPartida implements IComando {

    /**
     * Estado actual de la sala de espera donde se llevará a cabo la creación de la partida.
     */
    private EstadoSalaEspera estado;

    /**
     * Crea una instancia del comando con el estado de sala de espera dado.
     *
     * @param estado instancia del estado de sala de espera donde se gestionará la partida
     */
    public ComandoCrearPartida(EstadoSalaEspera estado) {
        this.estado = estado;
    }

    /**
     * Ejecuta el comando que maneja la respuesta del servidor al intento
     * de crear una nueva partida. La lógica se delega al estado actual.
     *
     * @param mensaje mapa con los datos necesarios para procesar la creación de la partida,
     *                como identificadores o configuraciones iniciales
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleCrearPartidaResponse(mensaje);
    }
}
