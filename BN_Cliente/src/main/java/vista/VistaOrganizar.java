/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import interfaz.IVistaOrganizar;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import tableroStrategy.ModoTableroStrategy;
import vistaModelo.Juego;
import vistaModelo.VistaModeloOrganizar;
import vistaModelo.VistaModeloTablero;

/**
 *
 * @author pauli
 */
public class VistaOrganizar implements IVistaPanel, IVistaOrganizar {

    /**
     * Panel principal del juego donde se agregan los componentes visuales.
     */
    private VistaPanel panelJuego;

    /**
     * Vista del tablero donde el jugador organiza sus unidades.
     */
    private VistaTablero tablero;

    /**
     * Selector de color para las naves.
     */
    private JComboBox<String> colorSelector;
    
    /**
     * Botón para colocar portaviones
     */
    private JButton botonPortaaviones;

    /**
     * Botón para confirmar que el jugador está listo para comenzar el juego.
     */
    private JButton botonJugar;

    /**
     * Panel que representa visualmente el portaaviones.
     */
    private JPanel portaaviones;

    /**
     * Panel que representa visualmente el crucero.
     */
    private JPanel crucero;

    /**
     * Panel que representa visualmente el submarino.
     */
    private JPanel submarino;

    /**
     * Panel que representa visualmente el barco.
     */
    private JPanel barco;

    /**
     * Etiqueta que muestra un mensaje cuando el jugador está esperando.
     */
    private JLabel labelEsperando;

    /**
     * vistaModelo que maneja la lógica de organización de las naves.
     */
    private VistaModeloOrganizar vistaModelo;
    
    

    /**
     * Imagen de portada utilizada en la vista de bienvenida.
     */
    private BufferedImage portada;
    
    /**
     * Imagen de titulo utilizada en la vista de buscar.
     */
    private BufferedImage titulo;
    
    private int contadorPortaaviones = 0;

    /**
     * Constructor de la clase VistaOrganizar. Inicializa el panel de juego,
     * crea los componentes y define sus acciones.
     *
     * @param panelJuego Panel principal del juego.
     */
    public VistaOrganizar(VistaPanel panelJuego) {
        this.panelJuego = panelJuego;
        this.vistaModelo = new VistaModeloOrganizar(this);
        
        crearComponentes();
        accionesComponentes();
        cargarImagenes();
    }

    /**
     * Dibuja la interfaz gráfica de la vista de organización del tablero.
     *
     * @param g Objeto Graphics para dibujar la interfaz.
     */
    @Override
    public void dibujar(Graphics g) {

        // Dibuja la imagen de fondo
        if (portada != null) {
            g.drawImage(portada, 0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO, null);
        }
        // Dibujar la imagen del titulo en la pantalla
        if (titulo != null) {
            g.drawImage(titulo, (Juego.GAME_ANCHO - titulo.getWidth()) / 2, 30, titulo.getWidth(), titulo.getHeight(), null);
        }

        g.setColor(VistaUtilidades.COLOR_TEXTO_BLANCO);
        //VistaUtilidades.dibujarTextoCentrado(g, "BATALLA NAVAL", 60, VistaUtilidades.FUENTE_TITULO);
        //VistaUtilidades.dibujarTextoCentrado(g, "Coloca tus naves en el tablero", 100, VistaUtilidades.FUENTE_SUBTITULO);
        //VistaUtilidades.dibujarTextoCentrado(g, "Presiona click izquierdo y arrastra la nave a las posiciones disponibles", 120, VistaUtilidades.FUENTE_SUBTITULO);
        //VistaUtilidades.dibujarTextoCentrado(g, "Presiona click derecho para rotar la nave (si hay espacio)", 140, VistaUtilidades.FUENTE_SUBTITULO);
        //VistaUtilidades.dibujarTextoCentrado(g, "Una nave no puede estar adyacente o sobre otra nave", 160, VistaUtilidades.FUENTE_SUBTITULO);
        VistaUtilidades.dibujarTextoCentrado(g, "Color de naves:", 155, VistaUtilidades.FUENTE_SUBTITULO);

        if (!panelJuego.isAncestorOf(tablero)) {
            tablero.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panelJuego.agregarComponente(tablero, 200, 200, 300, 300);
        }
        if (!panelJuego.isAncestorOf(colorSelector)) {
            panelJuego.agregarComponente(colorSelector, (Juego.GAME_ANCHO - 200) / 2, 165, 200, 30);
        }
        if (!panelJuego.isAncestorOf(botonJugar)) {
            //panelJuego.agregarComponente(botonJugar, (Juego.GAME_ANCHO - 200) / 2, Juego.GAME_ALTO - 80, 200, 30);
            int botonAncho = botonJugar.getPreferredSize().width;
            int botonAlto = botonJugar.getPreferredSize().height;
            int posX = (Juego.GAME_ANCHO - botonAncho) / 2;
            panelJuego.agregarComponente(botonJugar, posX, Juego.GAME_ALTO - 110, botonAncho, botonAlto);
        }
//        g.drawString("Portaaviones", 600, 220);antes 320 la Y
        if (!panelJuego.isAncestorOf(botonPortaaviones)) {
            panelJuego.agregarComponente(botonPortaaviones, 600,220, 200, 30);
        }
        if (!panelJuego.isAncestorOf(portaaviones)) {
            panelJuego.agregarComponente(portaaviones, 600, 230, (30 * 4), 30);//330Y
        }
        g.drawString("Crucero", 600, 290);//390
        if (!panelJuego.isAncestorOf(crucero)) {
            panelJuego.agregarComponente(crucero, 600, 300, (30 * 3), 30);//400
        }
        g.drawString("Submarino", 600, 360);
        if (!panelJuego.isAncestorOf(submarino)) {
            panelJuego.agregarComponente(submarino, 600, 370, (30 * 2), 30);
        }
        g.drawString("Barco", 600, 430);
        if (!panelJuego.isAncestorOf(barco)) {
            panelJuego.agregarComponente(barco, 600, 440, (30 * 1), 30);
        }
    }

    /**
     * Crea y configura los componentes gráficos de la vista de organización.
     */
    @Override
    public void crearComponentes() {
        this.tablero = new VistaTablero();
        this.tablero.setModo(ModoTableroStrategy.ORGANIZAR);
        this.colorSelector = VistaUtilidades.crearComboBox(VistaUtilidades.LISTA_COLORES_BARCO, 200, 30);
        this.colorSelector.setSelectedItem("Azul");
        this.botonJugar = VistaUtilidades.crearBotones(VistaUtilidades.BOTON_JUGAR);
        this.portaaviones = VistaUtilidades.crearBarcoVista((30 * 4), 30, VistaUtilidades.BARCO_AZUL);
        this.crucero = VistaUtilidades.crearBarcoVista((30 * 3), 30, VistaUtilidades.BARCO_AZUL);
        this.submarino = VistaUtilidades.crearBarcoVista((30 * 2), 30, VistaUtilidades.BARCO_AZUL);
        this.barco = VistaUtilidades.crearBarcoVista((30 * 1), 30, VistaUtilidades.BARCO_AZUL);
        this.botonPortaaviones = VistaUtilidades.crearBoton("Colocar PortaAviones");
    }

    /**
     * Define las acciones asociadas a los componentes de la vista.
     */
    @Override
    public void accionesComponentes() {
        // Agregar acción al botón
        botonJugar.addActionListener(e -> {
            tablero.setModo(ModoTableroStrategy.JUGADOR);
            vistaModelo.enviarUnidadesAlServidor();
        });
        // Agregar acción al selector
        colorSelector.addActionListener(e -> {
            String nombreColorSeleccionado = (String) colorSelector.getSelectedItem();
            vistaModelo.cambiarColorNaves(nombreColorSeleccionado);
        });
        
        botonPortaaviones.addActionListener(e -> {
          System.out.println("Click en botonPortaaviones. contadorPortaaviones antes: " + contadorPortaaviones);
    if (contadorPortaaviones <= 1) {
        
        tablero.colocarNave(contadorPortaaviones);
        contadorPortaaviones++;
    }
       System.out.println("contadorPortaaviones despues: " + contadorPortaaviones);
        });

    }

    /**
     * Quita los componentes gráficos del panel de juego.
     */
    @Override
    public void quitarComponentes() {
        this.panelJuego.quitarComponente(botonJugar);
        this.panelJuego.quitarComponente(colorSelector);
        this.panelJuego.quitarComponente(barco);
        this.panelJuego.quitarComponente(submarino);
        this.panelJuego.quitarComponente(crucero);
        this.panelJuego.quitarComponente(portaaviones);
        this.panelJuego.quitarComponente(tablero);
        if (labelEsperando != null) {
            this.panelJuego.quitarComponente(labelEsperando);
        }
    }

    /**
     * Muestra un mensaje indicando que un jugador está esperando.
     *
     * @param nombreJugador Nombre del jugador que está esperando.
     */
    @Override
    public void mostrarMensajeJugadorEsperando(String nombreJugador) {
        if (labelEsperando == null) {
            labelEsperando = new JLabel(nombreJugador + " está esperando...");
            labelEsperando.setForeground(VistaUtilidades.COLOR_TEXTO_BLANCO);
            labelEsperando.setFont(VistaUtilidades.FUENTE_SUBTITULO);
            panelJuego.agregarComponente(labelEsperando, (Juego.GAME_ANCHO - 200) / 2, Juego.GAME_ALTO - 40, 300, 30);
        } else {
            labelEsperando.setText(nombreJugador + " está esperando...");
        }
    }

    /**
     * Obtiene la vista del tablero.
     *
     * @return VistaTablero utilizada para organizar las unidades.
     */
    @Override
    public VistaTablero getTablero() {
        return tablero;
    }

    /**
     * Muestra un mensaje emergente con la información proporcionada.
     *
     * @param mensaje Mensaje a mostrar.
     */
    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(panelJuego, mensaje);
    }

    /**
     * Bloquea la interfaz para evitar la interacción del jugador.
     */
    @Override
    public void bloquearInterfaz() {
        // Deshabilitar botones y componentes para evitar interacción
        botonJugar.setEnabled(false);
        colorSelector.setEnabled(false);
        tablero.setEnabled(false);
    }

    /**
     * Navega a la fase de juego, quitando los componentes actuales.
     */
    @Override
    public void navegarAJugar() {
        quitarComponentes();

    }

    /**
     * Actualiza el color de los paneles laterales que representan las naves.
     *
     * @param nuevoColor Nuevo color a aplicar a los paneles.
     */
    @Override
    public void actualizarColorPanelesLaterales(Color nuevoColor) {
        portaaviones.setBackground(nuevoColor);
        crucero.setBackground(nuevoColor);
        submarino.setBackground(nuevoColor);
        barco.setBackground(nuevoColor);
    }

    /**
     * Obtiene el vistaModelo asociado a la vista de organización.
     *
     * @return VistaModeloOrganizar asociado a la vista.
     */
    public VistaModeloOrganizar getVistaModelo() {
        return vistaModelo;
    }
     public void cargarImagenes() {
        this.portada = VistaUtilidades.cargarImagen(VistaUtilidades.PORTADA);
        this.titulo = VistaUtilidades.cargarImagen(VistaUtilidades.TITULO);
    }
    
}
