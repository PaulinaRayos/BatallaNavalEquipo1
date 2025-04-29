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
 *
 * @author pauli
 */
public class VistaBuscarPartida implements IVistaPanel, IVistaBuscarPartida{
     /**
     * El panel principal del juego donde se agregarán los componentes de la vista.
     */
    private VistaPanel vistaPanel;
    
    /**
     * Imagen de portada utilizada en la vista de bienvenida.
     */
    private BufferedImage portada;
    
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
     * @param VistaPanel El panel principal del juego donde se agregarán los componentes de la vista.
     * @param juego La instancia del juego actual.
     */
    public VistaBuscarPartida(VistaPanel vistaPanel, Juego juego) {
        this.vistaPanel = vistaPanel;
        this.vistaModelo = new VistaModeloBuscarPartida(this, juego);
        crearComponentes();
        accionesComponentes();
        cargarImagenes();
    }

    /**
     * Dibuja la vista de buscar partida en el panel de juego.
     *
     * @param g El objeto Graphics utilizado para dibujar los elementos gráficos.
     */
    @Override
    public void dibujar(Graphics g) {
        if (portada != null) {
            g.drawImage(portada, 0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO, null);
        } 

        g.setColor(VistaUtilidades.COLOR_TEXTO_BLANCO);
        VistaUtilidades.dibujarTextoCentrado(g, "BUSCAR PARTIDA", 60, VistaUtilidades.FUENTE_TITULO);
        VistaUtilidades.dibujarTextoCentrado(g, "Introduce el codigo de la partida que deseas unirte", 150, VistaUtilidades.FUENTE_SUBTITULO);
        VistaUtilidades.dibujarTextoCentrado(g, "para que se pueda unir a la sala", 180, VistaUtilidades.FUENTE_SUBTITULO);
        VistaUtilidades.dibujarTextoCentrado(g, "Codigo de la sala:", 250, VistaUtilidades.FUENTE_SUBTITULO);

        // Agregar componentes al panel si no están ya agregados
        int botonAncho = botonContinuar.getPreferredSize().width;
        int botonAlto = botonContinuar.getPreferredSize().height;
        
//int posX = (Juego.GAME_ANCHO - botonAncho) / 2;
        if (!vistaPanel.isAncestorOf(botonContinuar)) {
            //vistaPanel.agregarComponente(botonContinuar, (Juego.GAME_ANCHO - 500) / 2, Juego.GAME_ALTO - 150, 200, 40);
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
     * Crea los componentes necesarios para la vista de buscar partida, como los botones y el campo de texto.
     */
    @Override
    public void crearComponentes() {
        botonContinuar = VistaUtilidades.crearBotones(VistaUtilidades.BOTON_CONTINUAR);
        botonSalir = VistaUtilidades.crearBotones(VistaUtilidades.BOTON_REGRESAR);
        campoSala = VistaUtilidades.crearCampoTexto(20);
    }

    /**
     * Define las acciones para los componentes de la vista, como los botones de continuar y salir.
     */
    @Override
    public void accionesComponentes() {
        // Agregar acción al botón
        botonContinuar.addActionListener(e -> {
            vistaModelo.unirseAPartida();
        });
        // Agregar acción al botón
        botonSalir.addActionListener(e -> {
            vistaModelo.regresarAlMenu();
        });
    }

    /**
     * Quita los componentes de la vista de buscar partida del panel de juego.
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
     * @param mensaje El mensaje informativo a mostrar.
     */
    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vistaPanel, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Obtiene el código de acceso ingresado en el campo de texto.
     *
     * @return El código de acceso ingresado.
     */
    @Override
    public String obtenerCodigoAcceso() {
        return campoSala.getText().trim();
    }

    /**
     * Navega a la sala de espera al quitar los componentes de la vista actual.
     */
    @Override
    public void navegarASalaDeEspera() {
        quitarComponentes();
        EstadosJuego.estado = EstadosJuego.SALA_ESPERA;
    }

    /**
     * Navega al menú principal al quitar los componentes de la vista actual.
     */
    @Override
    public void navegarAMenu() {
        quitarComponentes();
        EstadosJuego.estado = EstadosJuego.MENU;
    }

    /**
     * Obtiene el presentador asociado a esta vista.
     *
     * @return El presentador de buscar partida.
     */
    @Override
    public VistaModeloBuscarPartida getPresentador() {
        return vistaModelo;
    }
    
     
    public void cargarImagenes() {
        this.portada = VistaUtilidades.cargarImagen(VistaUtilidades.PORTADA);

    }

}
