/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import estados.EstadoMenu;
import interfaz.IVistaSalaEspera;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vistaModelo.Juego;
import vistaModelo.VistaModeloSalaEspera;

/**
 *
 * @author pauli
 */
public class VistaSalaEspera implements IVistaPanel, IVistaSalaEspera{
    /**
     * Panel principal del juego donde se mostrarán los componentes de esta vista.
     */
    private VistaPanel vistaPanel;
    
    
    /**
     * Imagen de portada utilizada en la vista de bienvenida.
     */
    private BufferedImage portada;
    
    /**
     * Botón para que el jugador indique que está listo para continuar.
     */
    private JButton botonContinuar;
    
    /**
     * Botón para que el jugador salga de la sala de espera.
     */
    private JButton botonSalir;
    
    /**
     * Tabla que muestra la lista de jugadores en la sala y su estado (listo o no listo).
     */
    private JTable listaJugadores;
    
    /**
     * Modelo de datos para la tabla de jugadores.
     */
    private DefaultTableModel modeloTabla;
    
    /**
     * Código de acceso de la sala que permite a otros jugadores unirse.
     */
    private String codigoAcceso;
    
    /**
     * Indica si el jugador actual está listo para continuar.
     */
    private boolean estoyListo = false;
    
    /**
     * Vista modelo asociado a la vista de la sala de espera, que maneja la lógica de negocio.
     */
    private VistaModeloSalaEspera vistaModelo;
    
    /**
     * Referencia al juego principal para cambiar de estado.
     */
    private Juego juego;
    
    /**
     * Constructor de la clase VistaSalaEspera.
     *
     * @param vistaPanel Panel principal del juego donde se mostrarán los componentes de esta vista.
     * @param juego Referencia al juego principal.
     */
    public VistaSalaEspera(VistaPanel vistaPanel, Juego juego) {
        this.vistaPanel = vistaPanel;
        this.vistaModelo = new VistaModeloSalaEspera(this);
        this.juego = juego;
        crearComponentes();
        accionesComponentes();
        cargarImagenes();
        
    }

    /**
     * Dibuja la vista de la sala de espera, mostrando el código de acceso y la lista de jugadores.
     *
     * @param g Objeto Graphics para dibujar los componentes en el panel.
     */
    @Override
    public void dibujar(Graphics g) {
        
        // Dibujar la imagen de fondo que ocupa toda la pantalla
        if (portada != null) {
            g.drawImage(portada, 0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO, null);
        } 
      
        g.setColor(VistaUtilidades.COLOR_TEXTO_BLANCO);
        VistaUtilidades.dibujarTextoCentrado(g, "SALA DE ESPERA", 60, VistaUtilidades.FUENTE_TITULO);
        VistaUtilidades.dibujarTextoCentrado(g, "Proporciona el código que se muestra debajo a otro jugador", 150, VistaUtilidades.FUENTE_SUBTITULO);
        VistaUtilidades.dibujarTextoCentrado(g, "para que se pueda unir a esta sala", 180, VistaUtilidades.FUENTE_SUBTITULO);
        VistaUtilidades.dibujarTextoCentrado(g, "Código de la sala:", 250, VistaUtilidades.FUENTE_SUBTITULO);
        VistaUtilidades.dibujarTextoCentrado(g, codigoAcceso != null ? codigoAcceso : "Esperando...", 280, VistaUtilidades.FUENTE_SUBTITULO);
        VistaUtilidades.dibujarTextoCentrado(g, "Lista de Jugadores en la sala", 410, VistaUtilidades.FUENTE_SUBTITULO);
        
        // Obtener dimensiones del botón
            int botonAncho = botonContinuar.getPreferredSize().width;
            int botonAlto = botonContinuar.getPreferredSize().height;

        // Calcular posición centrada horizontalmente
            int posX = (Juego.GAME_ANCHO - botonAncho) / 2;

        // Agregar componentes al panel si no están ya agregados
        if (!vistaPanel.isAncestorOf(botonContinuar)) {
            vistaPanel.agregarComponente(botonContinuar, (Juego.GAME_ANCHO - 500) / 2, Juego.GAME_ALTO - 150, botonAncho, botonAlto);
        }
        if (!vistaPanel.isAncestorOf(botonSalir)) {
            vistaPanel.agregarComponente(botonSalir, (Juego.GAME_ANCHO + 150) / 2, Juego.GAME_ALTO - 150, botonAncho, botonAlto);
        }
        if (!vistaPanel.isAncestorOf(listaJugadores)) {
            vistaPanel.agregarComponente(listaJugadores, (Juego.GAME_ANCHO - 400) / 2, Juego.GAME_ALTO - 300, 400, 60);
        }

    }
    
    /**
     * Crea los componentes de la interfaz gráfica de la vista de sala de espera.
     */
    @Override
    public void crearComponentes() {
        
        botonContinuar = VistaUtilidades.crearBotones(VistaUtilidades.BOTON_CONTINUAR);
        botonSalir = VistaUtilidades.crearBotones(VistaUtilidades.BOTON_REGRESAR);
        String[] columnas = {"Nombre de Jugador", "Listo"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        listaJugadores = new JTable(modeloTabla);
        // colocar las fuentes
        listaJugadores.setFont(VistaUtilidades.FUENTE_CAMPO_TEXTO);
        listaJugadores.setForeground(VistaUtilidades.COLOR_BOTON_FONDO);
        listaJugadores.setBackground(VistaUtilidades.COLOR_BOTON_TEXTO);

        listaJugadores.getTableHeader().setFont(VistaUtilidades.FUENTE_SUBTITULO);
        listaJugadores.getTableHeader().setForeground(VistaUtilidades.COLOR_BOTON_TEXTO);
        listaJugadores.getTableHeader().setBackground(VistaUtilidades.COLOR_BOTON_FONDO);
    }

    /**
     * Define las acciones de los componentes de la vista de sala de espera.
     */
    @Override
    public void accionesComponentes() {
        // Agregar acción al botón
        botonContinuar.addActionListener(e -> {
            vistaModelo.jugadorListo();
        });
        // Agregar acción al botón
        botonSalir.addActionListener(e -> {
            vistaModelo.salir();
        });
    }
    
    /**
     * Elimina los componentes de la vista de la sala de espera del panel principal.
     */
    @Override
    public void quitarComponentes() {
        vistaPanel.quitarComponente(botonContinuar);
        vistaPanel.quitarComponente(botonSalir);
        vistaPanel.quitarComponente(listaJugadores);
    }
    
    /**
     * Muestra el código de acceso de la sala en la interfaz gráfica.
     *
     * @param codigoAcceso Código de acceso de la sala.
     */
    @Override
    public void mostrarCodigoAcceso(String codigoAcceso) {
        this.codigoAcceso = codigoAcceso;
        vistaPanel.repaint();
    }

    /**
     * Agrega un jugador a la lista de jugadores en la sala.
     *
     * @param nombreJugador Nombre del jugador a agregar.
     */
    public void agregarJugador(String nombreJugador) {
        modeloTabla.addRow(new Object[]{nombreJugador});
    }
    
    /**
     * Agrega o actualiza un jugador en la lista de jugadores en la sala.
     *
     * @param nombreJugador Nombre del jugador.
     * @param listo Indica si el jugador está listo o no.
     */
    @Override
    public void agregarOActualizarJugador(String nombreJugador, boolean listo) {
        boolean encontrado = false;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if (modeloTabla.getValueAt(i, 0).equals(nombreJugador)) {
                // Actualizar el estado "Listo"
                modeloTabla.setValueAt(listo ? "Listo" : "No listo", i, 1);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            // Agregar el jugador a la tabla
            modeloTabla.addRow(new Object[]{nombreJugador, listo ? "Listo" : "No listo"});
        }
    }
    
    /**
     * Limpia la lista de jugadores en la sala.
     */
    @Override
    public void limpiarListaJugadores() {
        modeloTabla.setRowCount(0);
    }

    /**
     * Deshabilita el botón de continuar para que el jugador no pueda presionarlo.
     */
    @Override
    public void bloquearBotonContinuar() {
        botonContinuar.setEnabled(false);
    }

    /**
     * Muestra un mensaje emergente al jugador.
     *
     * @param mensaje Mensaje a mostrar.
     */
    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vistaPanel, mensaje);
    }

    /**
     * Navega al menú principal del juego.
     */
    @Override
    public void navegarAMenu() {
        quitarComponentes();
        juego.cambiarEstado(new EstadoMenu(juego));
    }

    /**
     * Navega a la vista para organizar el tablero del juego.
     */
    @Override
    public void navegarAOrganizar() {
        quitarComponentes();
    }

    /**
     * Verifica si el jugador está listo para continuar.
     *
     * @return {@code true} si el jugador está listo, de lo contrario {@code false}.
     */
    @Override
    public boolean isEstoyListo() {
        return estoyListo;
    }

    /**
     * Establece si el jugador está listo para continuar.
     *
     * @param listo {@code true} si el jugador está listo, de lo contrario {@code false}.
     */
    @Override
    public void setEstoyListo(boolean listo) {
        this.estoyListo = listo;
    }

    /**
     * Obtiene el presentador asociado a la vista de sala de espera.
     *
     * @return Presentador de la sala de espera.
     */
    @Override
    public VistaModeloSalaEspera getVistaModelo() {
        return vistaModelo;
    }

    /**
     * Establece el código de acceso de la sala.
     *
     * @param codigoAcceso Código de acceso de la sala.
     */
    public void setCodigoAcceso(String codigoAcceso) {
        this.codigoAcceso = codigoAcceso;
    }
    
    
    public void cargarImagenes() {
        this.portada = VistaUtilidades.cargarImagen(VistaUtilidades.PORTADA);

    }
}
