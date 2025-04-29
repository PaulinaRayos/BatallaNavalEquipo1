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
 *
 * @author pauli
 */
public class VistaBienvenida implements IVistaPanel, IVistaBienvenida {

    /**
     * El panel principal del juego donde se agregarán los componentes de la
     * vista.
     */
    private VistaPanel vistaPanel;

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
     * Vista modelo para gestionar la lógica de la vista de bienvenida.
     */
    private VistaModeloBienvenida vistaModelo;

    /**
     * Constructor de la clase VistaBienvenida.
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
     * @param g El objeto Graphics utilizado para dibujar los elementos
     * gráficos.
     */
    @Override
    public void dibujar(Graphics g) {
        // Dibujar la imagen de fondo que ocupa toda la pantalla
        if (portada != null) {
            g.drawImage(portada, 0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO, null);
        }

        g.setColor(VistaUtilidades.COLOR_TEXTO_BLANCO);
        VistaUtilidades.dibujarTextoCentrado(g, "Bienvenido a Batalla Naval", 60, VistaUtilidades.FUENTE_TITULO);
        VistaUtilidades.dibujarTextoCentrado(g, "Por favor, ingrese su nombre de usuario", 550, VistaUtilidades.FUENTE_SUBTITULO);
        VistaUtilidades.dibujarTextoCentrado(g, "El nombre puede ser de hasta 15 caracteres y estar compuesto por letras y numeros", 580, VistaUtilidades.FUENTE_SUBTITULO);

        if (!vistaPanel.isAncestorOf(campoNombre)) {
            vistaPanel.agregarComponente(campoNombre, (Juego.GAME_ANCHO - 200) / 2, Juego.GAME_ALTO - 120, 200, 30);
        }
        if (!vistaPanel.isAncestorOf(botonIniciar)) {
            //vistaPanel.agregarComponente(botonIniciar, (Juego.GAME_ANCHO - 200) / 2, Juego.GAME_ALTO - 80, 200, 40);
            // Obtener dimensiones del botón
            int botonAncho = botonIniciar.getPreferredSize().width;
            int botonAlto = botonIniciar.getPreferredSize().height;

            // Calcular posición centrada horizontalmente
            int posX = (Juego.GAME_ANCHO - botonAncho) / 2;

            // Agregar el botón con sus dimensiones originales
            vistaPanel.agregarComponente(botonIniciar, posX, Juego.GAME_ALTO - 80, botonAncho, botonAlto);
        }

    }

    /**
     * Crea los componentes necesarios para la vista de bienvenida, como el
     * campo de texto y el botón de iniciar.
     */
    public void crearComponentes() {
        campoNombre = VistaUtilidades.crearCampoTexto(60);
        //botonIniciar = VistaUtilidades.crearBoton("Iniciar Juego");
        botonIniciar = VistaUtilidades.crearBotones(VistaUtilidades.BOTON_INICIO);
    }

    /**
     * Define las acciones para los componentes de la vista, como el botón de
     * iniciar juego.
     */
    public void accionesComponentes() {
        // Agregar acción al botón
        botonIniciar.addActionListener(e -> {
            vistaModelo.iniciarJuego();
        });
    }

    /**
     * Quita los componentes de la vista de bienvenida del panel de juego.
     */
    public void quitarComponentes() {
        vistaPanel.quitarComponente(campoNombre);
        vistaPanel.quitarComponente(botonIniciar);
    }

    /**
     * Carga las imágenes necesarias para la vista de bienvenida.
     */
    public void cargarImagenes() {
        this.portada = VistaUtilidades.cargarImagen(VistaUtilidades.PORTADA);

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
