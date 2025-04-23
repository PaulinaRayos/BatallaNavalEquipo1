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
public class ComandoNuevoJugador implements IComando{
    /**
     * Estado de la sala de espera donde llega el nuevo jugador.
     */
    private EstadoSalaEspera estado;

    /**
     * Constructor que inicializa el comando con el estado de la sala de espera especificado.
     *
     * @param estado el estado de la sala de espera donde llega el nuevo jugador
     */
    public ComandoNuevoJugador(EstadoSalaEspera estado) {
        this.estado = estado;
    }

    /**
     * Ejecuta el comando para manejar la llegada de un nuevo jugador.
     *
     * @param mensaje un mapa que contiene los datos necesarios para manejar la llegada del nuevo jugador
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleNuevoJugador(mensaje);
    }
}
