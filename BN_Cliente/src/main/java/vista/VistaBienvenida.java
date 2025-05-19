/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import vistaModelo.Juego;
import vistaModelo.VistaModeloBienvenida;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import interfaz.IVistaBienvenida;

/**
 * Clase que representa la vista de bienvenida del juego.
 *
 * Esta clase implementa las interfaces IVistaPanel e IVistaBienvenida y
 * administra la interfaz gráfica para que el usuario ingrese su nombre y
 * comience la partida.
 *
 * Contiene componentes gráficos como botones, campos de texto e imágenes, y se
 * comunica con el VistaModelo para manejar la lógica de negocio.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class VistaBienvenida implements IVistaPanel, IVistaBienvenida {

    /**
     * El panel principal del juego donde se agregarán los componentes de la
     * vista.
     */
    private VistaPanel vistaPanel;

    /**
     * Vista del tablero vacío.
     */
    private VistaTablero tablero;

    /**
     * Campo de texto donde el jugador ingresa su nombre.
     */
    private JTextField campoNombre;

    /**
     * Botón para iniciar el juego.
     */
    private JButton botonIniciar;

    /**
     * Imagen de portada utilizada en la vista de bienvenida.
     */
    private BufferedImage portada;

    /**
     * Imagen del título utilizado en la vista de bienvenida.
     */
    private BufferedImage titulo;

    /**
     * Vista modelo para gestionar la lógica de la vista de bienvenida.
     */
    private VistaModeloBienvenida vistaModelo;

    /**
     * Constructor de la clase VistaBienvenida.
     *
     * Inicializa los componentes de la vista, configura acciones y carga
     * imágenes.
     *
     * @param vistaPanel El panel principal del juego donde se agregarán los
     * componentes de la vista.
     * @param juego La instancia del juego actual.
     */
    public VistaBienvenida(VistaPanel vistaPanel, Juego juego) {
        this.vistaPanel = vistaPanel;
        vistaModelo = new VistaModeloBienvenida(this, juego);
        crearComponentes();
        accionesComponentes();
        cargarImagenes();
    }

    /**
     * Dibuja la vista de bienvenida en el panel de juego.
     *
     * Muestra la imagen de portada y título, y dibuja texto e imágenes en
     * pantalla.
     *
     * @param g El objeto Graphics utilizado para dibujar los elementos
     * gráficos.
     */
    @Override
    public void dibujar(Graphics g) {
        if (portada != null) {
            g.drawImage(portada, 0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO, null);
        }
        if (titulo != null) {
            g.drawImage(titulo, (Juego.GAME_ANCHO - titulo.getWidth()) / 2, 60, titulo.getWidth(), titulo.getHeight(), null);
        }

        g.setColor(VistaUtilidades.COLOR_TEXTO_BLANCO);
        g.setFont(VistaUtilidades.FUENTE_SUBTITULO);

        g.drawString("Nombre:", 125, 220);
        g.drawString("Utiliza 15 caracteres", 125, 285);
        g.drawString("máximo y/o números", 125, 315);

        if (!vistaPanel.isAncestorOf(campoNombre)) {
            vistaPanel.agregarComponente(campoNombre, 125, 230, 200, 30);
        }
        if (!vistaPanel.isAncestorOf(botonIniciar)) {
            int botonAncho = botonIniciar.getPreferredSize().width;
            int botonAlto = botonIniciar.getPreferredSize().height;
            int posX = (Juego.GAME_ANCHO - botonAncho) / 2;
            vistaPanel.agregarComponente(botonIniciar, posX, Juego.GAME_ALTO - 150, botonAncho, botonAlto);
        }

        if (!vistaPanel.isAncestorOf(tablero)) {
            vistaPanel.agregarComponente(tablero, 490, 200, 300, 300);
        }
    }

    /**
     * Crea los componentes necesarios para la vista de bienvenida.
     *
     * Inicializa el campo de texto para nombre, el botón de inicio y la vista
     * del tablero.
     */
    public void crearComponentes() {
        campoNombre = VistaUtilidades.crearCampoTexto(60);
        botonIniciar = VistaUtilidades.crearBotones(VistaUtilidades.BOTON_INICIO);
        this.tablero = new VistaTablero();
    }

    /**
     * Define las acciones para los componentes de la vista.
     *
     * Agrega el listener al botón para iniciar el juego.
     */
    public void accionesComponentes() {
        botonIniciar.addActionListener(e -> {
            vistaModelo.iniciarJuego();
        });
    }

    /**
     * Quita los componentes de la vista de bienvenida del panel de juego.
     *
     * Remueve el campo de texto, botón y tablero del panel principal.
     */
    public void quitarComponentes() {
        vistaPanel.quitarComponente(campoNombre);
        vistaPanel.quitarComponente(botonIniciar);
        vistaPanel.quitarComponente(tablero);
    }

    /**
     * Obtiene la vista del tablero.
     *
     * @return VistaTablero utilizada para organizar las unidades.
     */
    public VistaTablero getTablero() {
        return tablero;
    }

    /**
     * Carga las imágenes necesarias para la vista de bienvenida.
     *
     * Carga la portada y el título desde los recursos.
     */
    public void cargarImagenes() {
        this.portada = VistaUtilidades.cargarImagen(VistaUtilidades.PORTADA);
        this.titulo = VistaUtilidades.cargarImagen(VistaUtilidades.TITULO);
    }

    /**
     * Muestra un mensaje de error en un cuadro de diálogo.
     *
     * @param mensaje El mensaje de error a mostrar.
     */
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(vistaPanel, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Obtiene el nombre del jugador ingresado en el campo de texto.
     *
     * @return El nombre del jugador ingresado.
     */
    public String obtenerNombreJugador() {
        return campoNombre.getText();
    }

}
