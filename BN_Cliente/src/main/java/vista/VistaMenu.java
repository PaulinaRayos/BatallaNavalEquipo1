/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import interfaz.IVistaMenu;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import vistaModelo.Juego;
import vistaModelo.VistaModeloMenu;

/**
 *
 * @author pauli
 */
public class VistaMenu implements IVistaPanel, IVistaMenu {

    /**
     * El panel de juego principal donde se agregarán los componentes de la
     * vista.
     */
    private VistaPanel panelJuego;

    /**
     * Botón para crear una partida nueva.
     */
    private JButton botonCrearPartida;

    /**
     * Botón para unirse a una partida existente.
     */
    private JButton botonUnirsePartida;


    /**
     * Imagen de portada utilizada en la vista de bienvenida.
     */
    private BufferedImage portada;

    /**
     * Imagen del titulo utilizado en la vista de bienvenida.
     */
    private BufferedImage titulo;

    /**
     * Presentador asociado a la vista del menú.
     */
    private VistaModeloMenu vistaModelo;

    /**
     * Constructor de la clase VistaMenu. Inicializa el presentador y los
     * componentes de la vista.
     *
     * @param panelJuego El panel de juego principal donde se agregarán los
     * componentes.
     * @param juego La instancia del juego actual.
     */
    public VistaMenu(VistaPanel panelJuego, Juego juego) {
        this.panelJuego = panelJuego;
        this.vistaModelo = new VistaModeloMenu(this, juego);
        crearComponentes();
        accionesComponentes();
        cargarImagenes();
    }

    /**
     * Dibuja la vista del menú en el panel de juego.
     *
     * @param g El objeto Graphics utilizado para dibujar los elementos
     * gráficos.
     */
    @Override
    public void dibujar(Graphics g) {
        if (portada != null) {
            g.drawImage(portada, 0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO, null);
        }
        // Dibujar la imagen del titulo en la pantalla
        if (titulo != null) {
            g.drawImage(titulo, (Juego.GAME_ANCHO - titulo.getWidth()) / 2, 60, titulo.getWidth(), titulo.getHeight(), null);
        }

//        g.setColor(VistaUtilidades.COLOR_TEXTO_BLANCO);
//        VistaUtilidades.dibujarTextoCentrado(g, "BATALLA NAVAL", 75, VistaUtilidades.FUENTE_TITULO);
        // Agregar componentes al panel si no están ya agregados
        if (!panelJuego.isAncestorOf(botonUnirsePartida)) {
            int botonAncho = botonUnirsePartida.getPreferredSize().width;
            int botonAlto = botonUnirsePartida.getPreferredSize().height;
            int posX = (Juego.GAME_ANCHO - botonAncho) / 2;
            panelJuego.agregarComponente(botonUnirsePartida, posX, Juego.GAME_ALTO - 500, botonAncho, botonAlto);
        }

        if (!panelJuego.isAncestorOf(botonCrearPartida)) {
            int botonAncho = botonCrearPartida.getPreferredSize().width;
            int botonAlto = botonCrearPartida.getPreferredSize().height;
            int posX = (Juego.GAME_ANCHO - botonAncho) / 2;
            panelJuego.agregarComponente(botonCrearPartida, posX, Juego.GAME_ALTO - 375, botonAncho, botonAlto);
        }
    }

    /**
     * Crea los componentes necesarios para la vista del menú, como los botones
     * de crear partida y unirse a partida
     */
    @Override
    public void crearComponentes() {
        this.botonUnirsePartida = VistaUtilidades.crearBotones(VistaUtilidades.BOTON_UNIRSE);
        this.botonCrearPartida = VistaUtilidades.crearBotones(VistaUtilidades.BOTON_CREAR);
    }

    /**
     * Define las acciones para los componentes de la vista, como los botones
     * del menú.
     */
    @Override
    public void accionesComponentes() {
        // Agregar acción al botón
        botonCrearPartida.addActionListener(e -> {
            vistaModelo.crearPartida();
        });
        // Agregar acción al botón
        botonUnirsePartida.addActionListener(e -> {
            vistaModelo.avanzarAUnirseAPartida();
        });
    }

    /**
     * Quita los componentes de la vista del menú del panel de juego.
     */
    @Override
    public void quitarComponentes() {
        panelJuego.quitarComponente(botonCrearPartida);
        panelJuego.quitarComponente(botonUnirsePartida);
    }

    public void cargarImagenes() {
        this.portada = VistaUtilidades.cargarImagen(VistaUtilidades.PORTADA);
        this.titulo = VistaUtilidades.cargarImagen(VistaUtilidades.TITULO);
    }

    /**
     * Muestra un mensaje de error en un cuadro de diálogo.
     *
     * @param mensaje El mensaje de error a mostrar.
     */
    @Override
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(panelJuego, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
