/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package eventos;

import java.util.Map;

/**
 * Esta interfaz define el contrato para los manejadores de eventos en el
 * sistema. Cualquier clase que desee manejar eventos debe implementar este
 * contrato, proporcionando su propia implementación del método `handle` para
 * procesar los datos del evento.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public interface EventHandler {

    /**
     * Método que debe ser implementado por cualquier clase que actúe como
     * manejador de un evento. Este método es responsable de procesar los datos
     * del evento cuando el evento es publicado en el EventBus.
     *
     * @param datos Mapa que contiene los datos asociados al evento. El
     * contenido de los datos depende del tipo de evento que se esté manejando.
     * @throws Exception Puede lanzar una excepción si ocurre un error durante
     * el manejo del evento.
     */
    void handle(Map<String, Object> datos) throws Exception;
}
