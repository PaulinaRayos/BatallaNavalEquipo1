/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import estados.EstadoOrganizar;
import java.util.Map;

/**
 * Comando que indica que el jugador debe esperar a que el oponente finalice su organización de barcos.
 * 
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ComandoJugadorEsperando implements IComando {

    /**
     * Estado de organización en el que se encuentra el jugador al esperar al oponente.
     */
    private EstadoOrganizar estado;

    /**
     * Crea un comando para manejar el estado de espera del jugador durante la fase de organización.
     *
     * @param estado el estado de organización actual del juego
     */
    public ComandoJugadorEsperando(EstadoOrganizar estado) {
        this.estado = estado;
    }
    
    /**
     * Ejecuta el comando que procesa el mensaje indicando que el jugador debe esperar.
     *
     * @param mensaje mapa que contiene información relacionada con el estado de espera
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleJugadorEsperando(mensaje);
    }
}