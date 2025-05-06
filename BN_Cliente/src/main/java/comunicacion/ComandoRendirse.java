/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import estados.EstadoJugar;
import java.util.Map;

/**
 *
 * @author pauli
 */
public class ComandoRendirse implements IComando {

    /**
     * Estado de juego en el que se realiza la rendición.
     */
    private EstadoJugar estado;

    /**
     * Constructor que inicializa el comando con el estado de juego especificado.
     *
     * @param estado el estado de juego donde se realiza la rendición
     */
    public ComandoRendirse(EstadoJugar estado) {
        this.estado = estado;
    }

    /**
     * Ejecuta el comando para manejar la rendición del jugador.
     *
     * @param mensaje un mapa que contiene los datos necesarios para manejar la rendición
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleRendirseResponse(mensaje);
    }

}
