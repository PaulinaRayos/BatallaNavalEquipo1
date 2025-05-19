/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import estados.EstadoJugar;
import java.util.Map;

/**
 * Comando que gestiona la acción de rendirse durante una partida. Invoca el
 * manejo de la respuesta correspondiente al jugador que decide rendirse.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ComandoRendirse implements IComando {

    /**
     * Estado actual del juego donde se procesa la rendición.
     */
    private EstadoJugar estado;

    /**
     * Constructor que inicializa el comando con el estado del juego donde se
     * produce la rendición.
     *
     * @param estado el estado de juego donde se realizará la acción de rendirse
     */
    public ComandoRendirse(EstadoJugar estado) {
        this.estado = estado;
    }

    /**
     * Ejecuta el comando para manejar la rendición del jugador.
     *
     * @param mensaje un mapa que contiene la información necesaria para
     * procesar la rendición
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleRendirseResponse(mensaje);
    }

}
