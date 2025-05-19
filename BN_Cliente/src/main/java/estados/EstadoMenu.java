/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estados;

import java.awt.Graphics;
import java.util.Map;
import vista.VistaMenu;
import vistaModelo.Juego;

/**
 * Representa el estado del juego correspondiente al menú principal.
 *
 * En este estado, se muestra la interfaz principal del juego desde donde el
 * usuario puede acceder a diferentes opciones como iniciar partida, ver
 * instrucciones, entre otras.
 *
 * Este estado no maneja mensajes externos por el momento.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class EstadoMenu implements IEstado {

    /**
     * Referencia al juego principal.
     */
    private Juego juego;

    /**
     * Vista que representa la interfaz del menú.
     */
    private VistaMenu vista;

    /**
     * Constructor que inicializa el estado del menú con el juego especificado.
     *
     * @param juego la referencia al juego principal
     */
    public EstadoMenu(Juego juego) {
        this.juego = juego;
        this.vista = new VistaMenu(juego.getPanel(), juego);
    }

    /**
     * Sale del estado del menú y quita los componentes de la vista.
     */
    @Override
    public void salir() {
        vista.quitarComponentes();
    }

    /**
     * Renderiza la vista del menú.
     *
     * @param g el objeto Graphics utilizado para dibujar la vista
     */
    @Override
    public void renderizar(Graphics g) {
        vista.dibujar(g);
    }

    /**
     * Maneja un mensaje recibido en el estado del menú.
     *
     * Actualmente, este estado no espera ni maneja mensajes.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    @Override
    public void handleMessage(Map<String, Object> mensaje) {
        // No se esperan mensajes en este estado al momento
    }
}
