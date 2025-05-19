/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import estados.EstadoSalaEspera;
import java.util.Map;

/**
 * Comando que gestiona la llegada de un nuevo jugador a la sala de espera.
 * 
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ComandoNuevoJugador implements IComando {

    /**
     * Estado actual de la sala de espera en el que se integrará el nuevo jugador.
     */
    private EstadoSalaEspera estado;

    /**
     * Crea una instancia del comando para manejar la llegada de un nuevo jugador.
     *
     * @param estado el estado actual de la sala de espera
     */
    public ComandoNuevoJugador(EstadoSalaEspera estado) {
        this.estado = estado;
    }

    /**
     * Ejecuta el comando que procesa la información relacionada con un nuevo jugador que se une a la sala.
     *
     * @param mensaje mapa que contiene los datos del nuevo jugador
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleNuevoJugador(mensaje);
    }
}
