/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableroStrategy;

import java.awt.event.MouseEvent;
import vista.VistaTablero;
import vistaModelo.VistaModeloTablero;

/**
 * Estrategia que implementa el modo de organización del tablero. En este modo,
 * el jugador puede organizar las unidades en el tablero mediante interacciones
 * con el ratón.
 *
 * Esta clase delega los eventos de ratón al VistaModelo correspondiente.
 *
 * @author pauli
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
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
     * Constructor que inicializa la estrategia con la vista y el vistaModelo
     * especificados.
     *
     * @param vista la vista del tablero
     * @param vistaModelo el vistaModelo del tablero
     */
    public ModoOrganizarStrategy(VistaTablero vista, VistaModeloTablero vistaModelo) {
        this.vista = vista;
        this.vistaModelo = vistaModelo;
    }

    /**
     * Maneja el evento cuando se presiona el ratón en el tablero para organizar
     * las unidades.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mousePressed(MouseEvent e) {
        vistaModelo.onMousePressed(e);
    }

    /**
     * Maneja el evento cuando se suelta el ratón en el tablero para organizar
     * las unidades.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        vistaModelo.onMouseReleased(e);
    }

    /**
     * Maneja el evento cuando se arrastra el ratón en el tablero para organizar
     * las unidades.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        vistaModelo.onMouseDragged(e);
    }

}
