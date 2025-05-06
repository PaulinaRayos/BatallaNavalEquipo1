/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import estados.EstadoSalaEspera;
import java.util.Map;

/**
 *
 * @author pauli
 */
public class ComandoIniciarOrganizar implements IComando {

    /**
     * Estado de la sala de espera desde el cual se iniciará la organización.
     */
    private EstadoSalaEspera estado;

    /**
     * Constructor que inicializa el comando con el estado de la sala de espera especificado.
     *
     * @param estado el estado de la sala de espera desde el cual se iniciará la organización
     */
    public ComandoIniciarOrganizar(EstadoSalaEspera estado) {
        this.estado = estado;
    }
    
    /**
     * Ejecuta el comando para iniciar la organización.
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleIniciarOrganizar();
    }
    
}
