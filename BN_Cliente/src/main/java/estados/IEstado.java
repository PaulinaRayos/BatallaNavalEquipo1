/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package estados;

import java.awt.Graphics;
import java.util.Map;

/**
 * Interfaz que define los métodos necesarios para representar un estado dentro
 * del juego.
 *
 * Cada estado debe poder manejar su salida, renderización visual y
 * procesamiento de mensajes.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public interface IEstado {

    /**
     * Salir del estado actual del juego.
     *
     * Este método se invoca cuando se realiza una transición desde este estado
     * hacia otro.
     */
    void salir();

    /**
     * Renderiza el estado actual del juego.
     *
     * @param g el objeto Graphics utilizado para dibujar el estado
     */
    void renderizar(Graphics g);

    /**
     * Maneja un mensaje recibido en el estado actual del juego.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    void handleMessage(Map<String, Object> mensaje);
}
