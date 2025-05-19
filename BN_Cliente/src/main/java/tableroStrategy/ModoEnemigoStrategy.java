/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableroStrategy;

import java.awt.event.MouseEvent;
import vista.VistaTablero;
import vistaModelo.VistaModeloTablero;

/**
 * Estrategia que implementa el modo enemigo para el tablero.
 *
 * En este modo se maneja la interacción con el tablero enemigo, permitiendo que
 * el usuario realice acciones solo si la interacción está habilitada en la
 * vista.
 *
 * Los eventos de suelta y arrastre del ratón no generan acción.
 *
 * @author pauli
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ModoEnemigoStrategy implements IModoTableroStrategy {

    /**
     * VistaModelo del tablero asociado a esta estrategia.
     */
    private VistaModeloTablero vistaModelo;

    /**
     * Vista del tablero asociado a esta estrategia.
     */
    private VistaTablero vista;

    /**
     * Constructor que inicializa la estrategia con la vista y el vistaModelo
     * especificados.
     *
     * @param vista la vista del tablero
     * @param vistaModelo el vistaModelo del tablero
     */
    public ModoEnemigoStrategy(VistaTablero vista, VistaModeloTablero vistaModelo) {
        this.vista = vista;
        this.vistaModelo = vistaModelo;
    }

    /**
     * Maneja el evento cuando se presiona el ratón en el tablero enemigo. Solo
     * procesa el click si la interacción está habilitada.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (!vista.isInteraccionHabilitada()) {
            return;
        }
        vista.manejarClickEnemigo(e);
    }

    /**
     * Maneja el evento cuando se suelta el ratón. No realiza ninguna acción en
     * este modo.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // No hace nada en este modo
    }

    /**
     * Maneja el evento cuando se arrastra el ratón. No realiza ninguna acción
     * en este modo.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        // No hace nada en este modo
    }
}
