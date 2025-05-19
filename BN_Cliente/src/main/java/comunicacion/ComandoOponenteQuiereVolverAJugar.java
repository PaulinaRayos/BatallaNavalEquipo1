/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import estados.EstadoEstadisticas;
import java.util.Map;

/**
 * Comando que maneja la situación cuando el oponente decide volver a jugar
 * después de una partida.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ComandoOponenteQuiereVolverAJugar implements IComando {

    /**
     * Estado actual de estadísticas para gestionar la decisión del oponente.
     */
    private EstadoEstadisticas estado;

    /**
     * Constructor que inicializa el comando con el estado de estadísticas
     * especificado.
     *
     * @param estado el estado de estadísticas que gestionará la decisión del
     * oponente
     */
    public ComandoOponenteQuiereVolverAJugar(EstadoEstadisticas estado) {
        this.estado = estado;
    }

    /**
     * Ejecuta el comando para procesar la respuesta del oponente que quiere
     * volver a jugar.
     *
     * @param mensaje un mapa con los datos necesarios para manejar la respuesta
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.manejarOponenteQuiereVolverAJugar(mensaje);
    }
}
