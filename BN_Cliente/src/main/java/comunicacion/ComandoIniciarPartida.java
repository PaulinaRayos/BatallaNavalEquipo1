/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import estados.EstadoEstadisticas;
import java.util.Map;

/**
 * Comando que permite iniciar una nueva partida desde la pantalla de estadísticas,
 * reiniciando el flujo del juego.
 * 
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ComandoIniciarPartida implements IComando {

    /**
     * Estado de estadísticas desde el cual se puede iniciar una nueva partida.
     */
    private EstadoEstadisticas estado;

    /**
     * Crea un comando que inicia una nueva partida desde el estado de estadísticas.
     *
     * @param estado instancia del estado actual de estadísticas
     */
    public ComandoIniciarPartida(EstadoEstadisticas estado) {
        this.estado = estado;
    }

    /**
     * Ejecuta el comando para comenzar una nueva partida.
     *
     * @param mensaje mapa con información necesaria para iniciar la nueva partida
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.manejarIniciarPartida(mensaje);
    }
}
