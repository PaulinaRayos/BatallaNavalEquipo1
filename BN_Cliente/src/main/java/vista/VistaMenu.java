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
 * Clase que representa la vista del menú principal del juego. Contiene botones
 * para crear o unirse a una partida, y muestra imágenes de portada y título.
 *
 * Implementa las interfaces IVistaPanel e IVistaMenu para integrarse con el
 * resto del sistema.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class VistaMenu implements IVistaPanel, IVistaMenu {

    /**
     * El panel principal del juego donde se agregarán los componentes visuales.
     */
    private VistaPanel panelJuego;

    /**
     * Botón que permite crear una nueva partida.
     */
    private JButton botonCrearPartida;

    /**
     * Botón que permite unirse a una partida existente.
     */
    private JButton botonUnirsePartida;

    /**
     * Imagen de fondo de la portada del menú.
     */
    private BufferedImage portada;

    /**
     * Imagen del título del juego que se muestra en el menú.
     */
    private BufferedImage titulo;

    /**
     * Vista modelo asociada a esta vista, que maneja la lógica del menú.
     */
    private VistaModeloMenu vistaModelo;

    /**
     * Constructor de la clase VistaMenu. Inicializa la vista modelo, carga
     * imágenes y configura los botones.
     *
     * @param panelJuego Panel principal donde se mostrarán los componentes.
     * @param juego Instancia del juego utilizada para la lógica del menú.
     */
    public VistaMenu(VistaPanel panelJuego, Juego juego) {
        this.panelJuego = panelJuego;
        this.vistaModelo = new VistaModeloMenu(this, juego);
        crearComponentes();
        accionesComponentes();
        cargarImagenes();
    }

    /**
     * Dibuja los elementos gráficos del menú sobre el panel principal. Se
     * muestran la imagen de portada, el título y los botones si no están
     * presentes.
     *
     * @param g Objeto Graphics utilizado para renderizar los elementos.
     */
    @Override
    public void dibujar(Graphics g) {
        if (portada != null) {
            g.drawImage(portada, 0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO, null);
        }

        if (titulo != null) {
            g.drawImage(titulo, (Juego.GAME_ANCHO - titulo.getWidth()) / 2, 60,
                    titulo.getWidth(), titulo.getHeight(), null);
        }

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
     * Crea los botones del menú (crear y unirse a partida) usando utilidades
     * comunes.
     */
    @Override
    public void crearComponentes() {
        this.botonUnirsePartida = VistaUtilidades.crearBotones(VistaUtilidades.BOTON_UNIRSE);
        this.botonCrearPartida = VistaUtilidades.crearBotones(VistaUtilidades.BOTON_CREAR);
    }

    /**
     * Asocia acciones a los botones del menú para que interactúen con la vista
     * modelo.
     */
    @Override
    public void accionesComponentes() {
        botonCrearPartida.addActionListener(e -> {
            vistaModelo.crearPartida();
        });

        botonUnirsePartida.addActionListener(e -> {
            vistaModelo.avanzarAUnirseAPartida();
        });
    }

    /**
     * Elimina los botones del menú del panel principal.
     */
    @Override
    public void quitarComponentes() {
        panelJuego.quitarComponente(botonCrearPartida);
        panelJuego.quitarComponente(botonUnirsePartida);
    }

    /**
     * Carga las imágenes necesarias para el menú, como la portada y el título.
     */
    public void cargarImagenes() {
        this.portada = VistaUtilidades.cargarImagen(VistaUtilidades.PORTADA);
        this.titulo = VistaUtilidades.cargarImagen(VistaUtilidades.TITULO);
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje de error.
     *
     * @param mensaje Mensaje de error a mostrar al usuario.
     */
    @Override
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(panelJuego, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
