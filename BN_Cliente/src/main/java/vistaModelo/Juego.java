/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistaModelo;

import comunicacion.ConexionCliente;
import vista.VistaPanel;
import vista.VistaVentana;
import java.awt.Graphics;
import java.util.Map;
import javax.swing.SwingUtilities;
import estados.IEstado;
import estados.EstadoBienvenida;

/**
 * Clase principal que representa el juego. Controla el ciclo principal,
 * renderizado, estados y la conexión con el servidor. Implementa Runnable para
 * ejecutar el loop de juego en un hilo separado.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class Juego implements Runnable {

    /**
     * Ventana principal del juego.
     */
    private VistaVentana ventana;

    /**
     * Panel donde se dibuja el contenido del juego.
     */
    protected VistaPanel panel;

    /**
     * Hilo que ejecuta el ciclo principal del juego.
     */
    private Thread hiloJuego;

    /**
     * Estado actual del juego (patrón estado).
     */
    private IEstado estadoActual;

    /**
     * Cuadros por segundo deseados para renderizado.
     */
    protected static final int FPS_SET = 60;

    /**
     * Actualizaciones por segundo deseadas para lógica.
     */
    protected static final int UPS_SET = 150;

    /**
     * Ancho de la ventana del juego.
     */
    public final static int GAME_ANCHO = 900;

    /**
     * Alto de la ventana del juego.
     */
    public final static int GAME_ALTO = 720;

    /**
     * Constructor que inicializa la ventana, panel, conexión y estado inicial
     * del juego.
     */
    public Juego() {
        // Crear panel y ventana
        panel = new VistaPanel(this);
        ventana = new VistaVentana(panel);
        panel.setFocusable(true);
        panel.requestFocus();

        // Iniciar conexión con servidor
        iniciarConexion();

        // Iniciar hilo principal del juego
        this.inicioJuegoLoop();

        // Establecer estado inicial: pantalla de bienvenida
        estadoActual = new EstadoBienvenida(this);
    }

    /**
     * Inicia el hilo que ejecuta el loop principal del juego.
     */
    private void inicioJuegoLoop() {
        hiloJuego = new Thread(this);
        hiloJuego.start();
    }

    /**
     * Método para dibujar el contenido del juego en pantalla. Delegado al
     * estado actual.
     *
     * @param g objeto Graphics usado para dibujar
     */
    public void renderizar(Graphics g) {
        if (estadoActual != null) {
            estadoActual.renderizar(g);
        }
    }

    /**
     * Loop principal que controla actualización y renderizado del juego,
     * regulando FPS y UPS y actualizando la pantalla continuamente.
     */
    public void run() {
        final int FPS = 60;
        final int UPS = 60;

        final double timePerUpdate = 1000000000.0 / UPS;
        final double timePerFrame = 1000000000.0 / FPS;

        long previousTime = System.nanoTime();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();
            double elapsedTime = currentTime - previousTime;
            previousTime = currentTime;

            deltaU += elapsedTime / timePerUpdate;
            deltaF += elapsedTime / timePerFrame;

            // Renderizado
            if (deltaF >= 1) {
                panel.repaint();
                deltaF--;
            }

            // Control del hilo para evitar uso excesivo de CPU
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Inicializa la conexión con el servidor y asigna un listener para mensajes
     * entrantes.
     */
    private void iniciarConexion() {
        ConexionCliente conexionCliente = ConexionCliente.getInstance();
        boolean conectado = conexionCliente.connect("localhost", 5000);
        if (conectado) {
            System.out.println("Conectado al servidor.");
            conexionCliente.setMessageListener(this::onMensajeRecibido);
        } else {
            System.out.println("No se pudo conectar al servidor.");
            // Aquí se puede agregar manejo de error adicional
        }
    }

    /**
     * Método que recibe mensajes del servidor y los pasa al estado actual para
     * ser procesados en el hilo de la interfaz gráfica.
     *
     * @param mensaje mensaje recibido del servidor
     */
    private void onMensajeRecibido(Map<String, Object> mensaje) {
        SwingUtilities.invokeLater(() -> {
            if (estadoActual != null) {
                estadoActual.handleMessage(mensaje);
            }
        });
    }

    /**
     * Cambia el estado actual del juego al nuevo estado especificado, llamando
     * al método salir del estado anterior.
     *
     * @param estadoNuevo nuevo estado del juego
     */
    public void cambiarEstado(IEstado estadoNuevo) {
        if (estadoActual != null) {
            estadoActual.salir();
        }
        estadoActual = estadoNuevo;
    }

    /**
     * Obtiene el panel principal del juego.
     *
     * @return panel donde se dibuja el juego
     */
    public VistaPanel getPanel() {
        return panel;
    }

}
