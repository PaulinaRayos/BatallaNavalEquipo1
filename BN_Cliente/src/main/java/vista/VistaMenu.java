/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import interfaz.IVistaMenu;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import vistaModelo.Juego;
import vistaModelo.VistaModeloMenu;

/**
 *
 * @author pauli
 */
public class VistaMenu implements IVistaPanel, IVistaMenu{
    /**
     * El panel de juego principal donde se agregarán los componentes de la vista.
     */
    private VistaPanel panelJuego;
    
    /**
     * Botón para crear una partida nueva.
     */
    private JButton botonCrearPartia;
    
    /**
     * Botón para unirse a una partida existente.
     */
    private JButton botonUnirsePartida;
    
    /**
     * Botón para acceder a las instrucciones del juego.
     */
    private JButton botonInstrucciones;
    
    /**
     * Presentador asociado a la vista del menú.
     */
    private VistaModeloMenu vistaModelo;

    /**
     * Constructor de la clase VistaMenu.
     * Inicializa el presentador y los componentes de la vista.
     *
     * @param panelJuego El panel de juego principal donde se agregarán los componentes.
     * @param juego La instancia del juego actual.
     */
    public VistaMenu(VistaPanel panelJuego, Juego juego) {
        this.panelJuego = panelJuego;
        this.vistaModelo = new VistaModeloMenu(this, juego);
        crearComponentes();
        accionesComponentes();
    }

    /**
     * Dibuja la vista del menú en el panel de juego.
     *
     * @param g El objeto Graphics utilizado para dibujar los elementos gráficos.
     */
    @Override
    public void dibujar(Graphics g) {
        g.setColor(VistaUtilidades.COLOR_FONDO);
        g.fillRect(0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO);

        g.setColor(VistaUtilidades.COLOR_TEXTO_AZUL_OSCURO);
        VistaUtilidades.dibujarTextoCentrado(g, "MENU", 60, VistaUtilidades.FUENTE_TITULO);

        // Agregar componentes al panel si no están ya agregados
        if (!panelJuego.isAncestorOf(botonCrearPartia)) {
            panelJuego.agregarComponente(botonCrearPartia, (Juego.GAME_ANCHO - 200) / 2, Juego.GAME_ALTO - 500, 200, 40);
        }
        if (!panelJuego.isAncestorOf(botonUnirsePartida)) {
            panelJuego.agregarComponente(botonUnirsePartida, (Juego.GAME_ANCHO - 200) / 2, Juego.GAME_ALTO - 400, 200, 40);
        }
        if (!panelJuego.isAncestorOf(botonInstrucciones)) {
            panelJuego.agregarComponente(botonInstrucciones, (Juego.GAME_ANCHO - 200) / 2, Juego.GAME_ALTO - 300, 200, 40);
        }

    }

    /**
     * Crea los componentes necesarios para la vista del menú, como los botones de crear partida, unirse a partida e instrucciones.
     */
    @Override
    public void crearComponentes() {
        this.botonCrearPartia = VistaUtilidades.crearBoton("Crear partida");
        this.botonUnirsePartida = VistaUtilidades.crearBoton("Unirse a partida");
        this.botonInstrucciones = VistaUtilidades.crearBoton("Instrucciones");
    }

    /**
     * Define las acciones para los componentes de la vista, como los botones del menú.
     */
    @Override
    public void accionesComponentes() {
        // Agregar acción al botón
        botonCrearPartia.addActionListener(e -> {
            vistaModelo.crearPartida();
        });
        // Agregar acción al botón
        /**botonInstrucciones.addActionListener(e -> {*************************************************************************
            vistaModelo.avanzarAInstrucciones();
        });*///*******************************************************************************************************************
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
        panelJuego.quitarComponente(botonCrearPartia);
        panelJuego.quitarComponente(botonUnirsePartida);
        panelJuego.quitarComponente(botonInstrucciones);
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
