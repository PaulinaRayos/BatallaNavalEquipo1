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
public class ModoOrganizarStrategy implements IModoTableroStrategy {
    
    /**
     * VistaModelo del tablero asociado a esta estrategia.
     */
    private VistaModeloTablero vistaModelo;
    
    /**
     * Vista del tablero asociado a esta estrategia.
     */
    private VistaTablero vista;
    
    /**
     * Constructor que inicializa la estrategia con la vista y el presentador especificados.
     *
     * @param vista la vista del tablero
     * @param vistaModelo el vistaModelo del tablero
     */
    public ModoOrganizarStrategy(VistaTablero vista, VistaModeloTablero vistaModelo) {
        this.vista = vista;
        this.vistaModelo = vistaModelo;
    }

    /**
     * Maneja el evento cuando se presiona el ratón en el tablero para organizar las unidades.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mousePressed(MouseEvent e) {
        vistaModelo.onMousePressed(e);
    }

    /**
     * Maneja el evento cuando se suelta el ratón en el tablero para organizar las unidades.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        vistaModelo.onMouseReleased(e);
    }

    /**
     * Maneja el evento cuando se arrastra el ratón en el tablero para organizar las unidades.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        vistaModelo.onMouseDragged(e);
    }
    
}
