/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import interfaz.IVistaBuscarPartida;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import vistaModelo.Juego;
import vistaModelo.VistaModeloBuscarPartida;

/**
 * Clase que representa la vista para buscar y unirse a una partida.
 *
 * Esta clase implementa las interfaces IVistaPanel e IVistaBuscarPartida y se
 * encarga de mostrar la interfaz gráfica donde el usuario puede ingresar el
 * código de una sala para unirse a una partida existente.
 *
 * Contiene componentes gráficos como botones, campos de texto, y maneja la
 * interacción con el usuario a través del presentador VistaModeloBuscarPartida.
 *
 * Utiliza imágenes para la portada y título, y texto informativo para guiar al
 * usuario.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class VistaBuscarPartida implements IVistaPanel, IVistaBuscarPartida {

    /**
     * El panel principal del juego donde se agregarán los componentes de la
     * vista.
     */
    private VistaPanel vistaPanel;

    /**
     * Imagen de portada utilizada en la vista de bienvenida.
     */
    private BufferedImage portada;

    /**
     * Imagen de título utilizada en la vista de buscar partida.
     */
    private BufferedImage titulo;

    /**
     * Botón para continuar y unirse a la partida.
     */
    private JButton botonContinuar;

    /**
     * Botón para salir y regresar al menú principal.
     */
    private JButton botonSalir;

    /**
     * Campo de texto donde el jugador ingresa el código de la sala.
     */
    private JTextField campoSala;

    /**
     * Presentador para gestionar la lógica de la vista de buscar partida.
     */
    private VistaModeloBuscarPartida vistaModelo;

    /**
     * Constructor de la clase VistaBuscarPartida.
     *
     * Inicializa los componentes visuales y la lógica de presentación, además
     * de cargar las imágenes necesarias para la vista.
     *
     * @param vistaPanel El panel principal del juego donde se agregarán los
     * componentes.
     * @param juego La instancia del juego actual para interacción con el
     * modelo.
     */
    public VistaBuscarPartida(VistaPanel vistaPanel, Juego juego) {
        this.vistaPanel = vistaPanel;
        this.vistaModelo = new VistaModeloBuscarPartida(this, juego);
        crearComponentes();
        accionesComponentes();
        cargarImagenes();
    }

    /**
     * Dibuja la vista de buscar partida en el panel principal.
     *
     * Pinta la imagen de portada, el título y textos informativos. Además,
     * añade los componentes de la interfaz (botones y campo de texto) al panel
     * si aún no están agregados.
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
        VistaUtilidades.dibujarTextoCentrado(g, "Introduce el código de la partida que deseas unirte", 185, VistaUtilidades.FUENTE_SUBTITULO);
        VistaUtilidades.dibujarTextoCentrado(g, "para que se pueda unir a la sala", 215, VistaUtilidades.FUENTE_SUBTITULO);
        VistaUtilidades.dibujarTextoCentrado(g, "Código de la sala:", 270, VistaUtilidades.FUENTE_SUBTITULO);

        int botonAncho = botonContinuar.getPreferredSize().width;
        int botonAlto = botonContinuar.getPreferredSize().height;

        if (!vistaPanel.isAncestorOf(botonContinuar)) {
            vistaPanel.agregarComponente(botonContinuar, (Juego.GAME_ANCHO - 500) / 2, Juego.GAME_ALTO - 150, botonAncho, botonAlto);
        }
        if (!vistaPanel.isAncestorOf(botonSalir)) {
            vistaPanel.agregarComponente(botonSalir, (Juego.GAME_ANCHO + 150) / 2, Juego.GAME_ALTO - 150, botonAncho, botonAlto);
        }
        if (!vistaPanel.isAncestorOf(campoSala)) {
            vistaPanel.agregarComponente(campoSala, (Juego.GAME_ANCHO - 200) / 2, 300, 200, 30);
        }
    }

    /**
     * Crea los componentes gráficos necesarios para la vista, incluyendo
     * botones y campo de texto.
     */
    @Override
    public void crearComponentes() {
        botonContinuar = VistaUtilidades.crearBotones(VistaUtilidades.BOTON_CONTINUAR);
        botonSalir = VistaUtilidades.crearBotones(VistaUtilidades.BOTON_REGRESAR);
        campoSala = VistaUtilidades.crearCampoTexto(20);
    }

    /**
     * Define las acciones para los botones de la vista, conectando los eventos
     * a la lógica en el presentador.
     */
    @Override
    public void accionesComponentes() {
        botonContinuar.addActionListener(e -> vistaModelo.unirseAPartida());
        botonSalir.addActionListener(e -> vistaModelo.regresarAlMenu());
    }

    /**
     * Elimina los componentes visuales de esta vista del panel principal, para
     * limpiar la interfaz al cambiar de vista.
     */
    @Override
    public void quitarComponentes() {
        vistaPanel.quitarComponente(botonContinuar);
        vistaPanel.quitarComponente(botonSalir);
        vistaPanel.quitarComponente(campoSala);
    }

    /**
     * Muestra un mensaje informativo en un cuadro de diálogo.
     *
     * @param mensaje El mensaje a mostrar al usuario.
     */
    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vistaPanel, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Obtiene el código de acceso ingresado por el usuario en el campo de
     * texto.
     *
     * @return El código de acceso ingresado, sin espacios al inicio o fin.
     */
    @Override
    public String obtenerCodigoAcceso() {
        return campoSala.getText().trim();
    }

    /**
     * Navega a la sala de espera, quitando los componentes de la vista actual.
     */
    @Override
    public void navegarASalaDeEspera() {
        quitarComponentes();
        EstadosJuego.estado = EstadosJuego.SALA_ESPERA;
    }

    /**
     * Navega al menú principal, quitando los componentes de la vista actual.
     */
    @Override
    public void navegarAMenu() {
        quitarComponentes();
        EstadosJuego.estado = EstadosJuego.MENU;
    }

    /**
     * Obtiene el presentador (controlador) asociado a esta vista.
     *
     * @return El presentador VistaModeloBuscarPartida.
     */
    @Override
    public VistaModeloBuscarPartida getPresentador() {
        return vistaModelo;
    }

    /**
     * Carga las imágenes necesarias para la vista, como la portada y el título.
     */
    public void cargarImagenes() {
        this.portada = VistaUtilidades.cargarImagen(VistaUtilidades.PORTADA);
        this.titulo = VistaUtilidades.cargarImagen(VistaUtilidades.TITULO);
    }
}
