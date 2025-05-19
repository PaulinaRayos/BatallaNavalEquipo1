/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import estados.EstadoEstadisticas;
import java.util.Map;

/**
 * Comando que gestiona la situación cuando el oponente decide no volver a jugar
 * después de una partida.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ComandoOponenteNoQuiereVolverAJugar implements IComando {

    /**
     * Estado actual de estadísticas al final de la partida.
     */
    private EstadoEstadisticas estado;

    /**
     * Crea una instancia del comando para manejar la negativa del oponente a
     * jugar otra partida.
     *
     * @param estado el estado actual de estadísticas del juego
     */
    public ComandoOponenteNoQuiereVolverAJugar(EstadoEstadisticas estado) {
        this.estado = estado;
    }

    /**
     * Ejecuta el comando que procesa la decisión del oponente de no volver a
     * jugar.
     *
     * @param mensaje mapa que contiene los datos relacionados con la decisión
     * del oponente
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.manejarOponenteNoQuiereVolverAJugar(mensaje);
    }
}
