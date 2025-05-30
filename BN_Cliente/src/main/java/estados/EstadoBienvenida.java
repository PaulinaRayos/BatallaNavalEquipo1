/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estados;

import java.awt.Graphics;
import java.util.Map;
import vista.VistaBienvenida;
import vistaModelo.Juego;

/**
 * Estado del juego que representa la pantalla de bienvenida, donde se muestra
 * la introducción inicial del juego.
 *
 * Este estado es estático y no espera mensajes entrantes.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class EstadoBienvenida implements IEstado {

    /**
     * Referencia al juego principal.
     */
    private Juego juego;

    /**
     * Vista que representa la interfaz de bienvenida.
     */
    private VistaBienvenida vista;

    /**
     * Constructor que inicializa el estado de bienvenida con el juego
     * especificado.
     *
     * @param juego la referencia al juego principal
     */
    public EstadoBienvenida(Juego juego) {
        this.juego = juego;
        this.vista = new VistaBienvenida(juego.getPanel(), juego);
    }

    /**
     * Sale del estado de bienvenida y quita los componentes de la vista.
     */
    @Override
    public void salir() {
        vista.quitarComponentes();
    }

    /**
     * Renderiza la vista de bienvenida.
     *
     * @param g el objeto Graphics utilizado para dibujar la vista
     */
    @Override
    public void renderizar(Graphics g) {
        vista.dibujar(g);
    }

    /**
     * Maneja un mensaje recibido en el estado de bienvenida. Actualmente no se
     * esperan mensajes en este estado.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    @Override
    public void handleMessage(Map<String, Object> mensaje) {
        // No se esperan mensajes en este estado actualmente.
    }
}
