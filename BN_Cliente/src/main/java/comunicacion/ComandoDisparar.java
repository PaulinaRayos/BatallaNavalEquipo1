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
public class ComandoDisparar implements IComando {

    /**
     * Estado del juego en el que se realizará el ataque.
     */
    private EstadoJugar estado;

    /**
     * Constructor que inicializa el comando con el estado del juego especificado.
     *
     * @param estado el estado del juego en el que se realizará el ataque
     */
    public ComandoDisparar(EstadoJugar estado) {
        this.estado = estado;
    }
    
    /**
     * Ejecuta el comando para manejar la respuesta del ataque.
     *
     * @param mensaje un mapa que contiene los datos necesarios para manejar el ataque
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleAtacarResponse(mensaje);
    }
    
}
