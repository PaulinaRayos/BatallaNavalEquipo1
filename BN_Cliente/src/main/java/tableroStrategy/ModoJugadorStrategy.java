/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableroStrategy;

import java.awt.event.MouseEvent;

/**
 * Estrategia que implementa el modo jugador para el tablero.
 *
 * En este modo, no se realiza ninguna acción en respuesta a los eventos del
 * ratón.
 *
 * Es útil para representar estados en los que la interacción directa con el
 * tablero está deshabilitada o no es necesaria.
 *
 * @author pauli
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ModoJugadorStrategy implements IModoTableroStrategy {

    /**
     * Maneja el evento cuando se presiona el ratón. En este modo no se realiza
     * ninguna acción.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // No hace nada en este modo
    }

    /**
     * Maneja el evento cuando se suelta el ratón. En este modo no se realiza
     * ninguna acción.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // No hace nada en este modo
    }

    /**
     * Maneja el evento cuando se arrastra el ratón. En este modo no se realiza
     * ninguna acción.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        // No hace nada en este modo
    }

}
