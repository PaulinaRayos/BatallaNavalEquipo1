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
public class ComandoIniciarJuego implements IComando {

    /**
     * Estado de organización desde el cual se iniciará el juego.
     */
    private EstadoOrganizar estado;

    /**
     * Constructor que inicializa el comando con el estado de organización especificado.
     *
     * @param estado el estado de organización desde el cual se iniciará el juego
     */
    public ComandoIniciarJuego(EstadoOrganizar estado) {
        this.estado = estado;
    }
    
    /**
     * Ejecuta el comando para iniciar el juego.
     *
     * @param mensaje un mapa que contiene los datos necesarios para iniciar el juego
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleIniciarJuego(mensaje);
    }
    
}
