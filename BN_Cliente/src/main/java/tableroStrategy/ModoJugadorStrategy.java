/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableroStrategy;

import java.awt.event.MouseEvent;

/**
 *
 * @author pauli
 */
public class ModoJugadorStrategy implements IModoTableroStrategy {

    /**
     * Maneja el evento cuando se presiona el ratón. No tiene comportamiento en este modo.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // No hace nada en este modo
    }

    /**
     * Maneja el evento cuando se suelta el ratón. No tiene comportamiento en este modo.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // No hace nada en este modo
    }

    /**
     * Maneja el evento cuando se arrastra el ratón. No tiene comportamiento en este modo.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        // No hace nada en este modo
    }
    
}
