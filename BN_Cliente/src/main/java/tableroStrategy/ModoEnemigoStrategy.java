/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableroStrategy;

import java.awt.event.MouseEvent;
import vista.VistaTablero;
import vistaModelo.VistaModeloTablero;

/**
 *
 * @author pauli
 */
public class ModoEnemigoStrategy implements IModoTableroStrategy{
    /**
     * Presentador del tablero asociado a esta estrategia.
     */
    private VistaModeloTablero vistaModelo;
    
    /**
     * Vista del tablero asociado a esta estrategia.
     */
    private VistaTablero vista;

    /**
     * Constructor que inicializa la estrategia con la vista y el vistaModelo especificados.
     *
     * @param vista la vista del tablero
     * @param vistaModelo el presentador del tablero
     */
    public ModoEnemigoStrategy(VistaTablero vista, VistaModeloTablero vistaModelo) {
        this.vista = vista;
        this.vistaModelo = vistaModelo;
    }

    /**
     * Maneja el evento cuando se presiona el ratón en el tablero enemigo.
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