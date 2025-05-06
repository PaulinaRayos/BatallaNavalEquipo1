/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tableroStrategy;

import java.awt.event.MouseEvent;

/**
 *
 * @author pauli
 */
public interface IModoTableroStrategy {
    /**
     * Maneja el evento cuando se presiona el ratón.
     *
     * @param e el evento de ratón
     */
    void mousePressed(MouseEvent e);

    /**
     * Maneja el evento cuando se suelta el ratón.
     *
     * @param e el evento de ratón
     */
    void mouseReleased(MouseEvent e);

    /**
     * Maneja el evento cuando se arrastra el ratón.
     *
     * @param e el evento de ratón
     */
    void mouseDragged(MouseEvent e);
}
