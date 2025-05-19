/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import estados.EstadoJugar;
import java.util.Map;

/**
 * Comando que gestiona la acción de disparar durante la partida. La lógica de
 * manejo de la respuesta se delega al estado de juego actual.
 *
 * Este comando forma parte del sistema de comunicación del juego, donde cada
 * acción del jugador es encapsulada en un objeto comando.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ComandoDisparar implements IComando {

    /**
     * Estado actual del juego, encargado de procesar la acción de disparar.
     */
    private EstadoJugar estado;

    /**
     * Crea una instancia del comando para disparar, asociada a un estado de
     * juego.
     *
     * @param estado instancia del estado de juego donde se manejará la acción
     * de disparo
     */
    public ComandoDisparar(EstadoJugar estado) {
        this.estado = estado;
    }

    /**
     * Ejecuta el comando para procesar la respuesta al disparo. Esta respuesta
     * puede incluir información como el resultado del disparo (impacto, fallo,
     * hundimiento, etc.) y se delega al estado de juego.
     *
     * @param mensaje mapa con los datos relacionados al resultado del ataque
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleAtacarResponse(mensaje);
    }
}
