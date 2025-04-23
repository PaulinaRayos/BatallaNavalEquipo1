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
public class ComandoCrearPartida implements IComando{
    /**
     * Estado de la sala de espera donde se creará la partida.
     */
    private EstadoSalaEspera estado;

    /**
     * Constructor que inicializa el comando con el estado de la sala de espera especificado.
     *
     * @param estado el estado de la sala de espera donde se creará la partida
     */
    public ComandoCrearPartida(EstadoSalaEspera estado) {
        this.estado = estado;
    }
    
    /**
     * Ejecuta el comando para manejar la respuesta de creación de partida.
     *
     * @param mensaje un mapa que contiene los datos necesarios para manejar la creación de la partida
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleCrearPartidaResponse(mensaje);
    }
    
}
