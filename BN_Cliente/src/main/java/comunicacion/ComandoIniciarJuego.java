/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import estados.EstadoOrganizar;
import java.util.Map;

/**
 * Comando que indica el inicio de la partida desde el estado de organización.
 * Este comando es invocado cuando ambos jugadores están listos y se debe
 * transitar hacia el estado de juego activo.
 * 
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ComandoIniciarJuego implements IComando {

    /**
     * Estado actual de organización, encargado de iniciar el juego.
     */
    private EstadoOrganizar estado;

    /**
     * Crea una instancia del comando asociada al estado de organización.
     *
     * @param estado instancia del estado desde el cual se iniciará la partida
     */
    public ComandoIniciarJuego(EstadoOrganizar estado) {
        this.estado = estado;
    }

    /**
     * Ejecuta el comando para procesar el mensaje de inicio de juego.
     * Este método delega al estado correspondiente la lógica para
     * inicializar las condiciones del juego.
     *
     * @param mensaje mapa con los datos necesarios para iniciar la partida
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleIniciarJuego(mensaje);
    }
}