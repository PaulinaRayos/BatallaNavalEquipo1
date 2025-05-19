/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import estados.EstadoBuscarPartida;
import java.util.Map;

/**
 * Comando que maneja la acción de unirse a una partida desde el estado de
 * búsqueda de partida.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ComandoUnirsePartida implements IComando {

    /**
     * Estado de búsqueda de partida donde se realiza la unión a una partida.
     */
    private EstadoBuscarPartida estado;

    /**
     * Constructor que inicializa el comando con el estado de búsqueda de
     * partida especificado.
     *
     * @param estado el estado de búsqueda de partida donde se realiza la unión
     * a una partida
     */
    public ComandoUnirsePartida(EstadoBuscarPartida estado) {
        this.estado = estado;
    }

    /**
     * Ejecuta el comando para manejar la respuesta de unión a la partida.
     *
     * @param mensaje un mapa que contiene los datos necesarios para manejar la
     * unión a la partida
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleUnirsePartidaResponse(mensaje);
    }
}
