/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import estados.EstadoOrganizar;
import java.util.Map;

/**
 *
 * @author pauli
 */
public class ComandoJugadorEsperando implements IComando {

    /**
     * Estado de organización donde el jugador está esperando.
     */
    private EstadoOrganizar estado;

    /**
     * Constructor que inicializa el comando con el estado de organización especificado.
     *
     * @param estado el estado de organización donde el jugador está esperando
     */
    public ComandoJugadorEsperando(EstadoOrganizar estado) {
        this.estado = estado;
    }
    
    /**
     * Ejecuta el comando para manejar el estado de espera del jugador.
     *
     * @param mensaje un mapa que contiene los datos necesarios para manejar la espera del jugador
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleJugadorEsperando(mensaje);
    }
    
}
