/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package comunicacion;

import java.util.Map;

/**
 *
 * Interfaz que define el contrato para ejecutar comandos a partir de un mensaje
 * recibido en forma de mapa clave-valor.
 *
 * Cada comando implementará esta interfaz para realizar una acción específica
 * basada en los datos contenidos en el mensaje.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public interface IComando {

    /**
     * Ejecuta la acción correspondiente al comando, utilizando la información
     * contenida en el mensaje recibido.
     *
     * @param mensaje Mapa con datos clave-valor que contiene la información
     * necesaria para la ejecución del comando.
     */
    void execute(Map<String, Object> mensaje);
}
