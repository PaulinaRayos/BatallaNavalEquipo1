/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estados;

import comunicacion.ComandoDisparar;
import comunicacion.ComandoRendirse;
import comunicacion.IComando;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import vista.VistaJuego;
import vista.VistaTablero;
import vistaModelo.Juego;
import vistaModelo.VistaModeloJuego;

/**
 * Representa el estado del juego en el que los jugadores interactúan
 * directamente en la batalla.
 *
 * En este estado se manejan acciones como atacar, rendirse y se actualiza la
 * lógica del turno y resultado.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class EstadoJugar implements IEstado {

    /**
     * Referencia al juego principal.
     */
    private Juego juego;

    /**
     * Vista que representa la interfaz de juego.
     */
    private VistaJuego vista;

    /**
     * VistaModelo asociado a la vista de juego.
     */
    private VistaModeloJuego vistaModelo;

    /**
     * Mapa que contiene los comandos disponibles en el estado de juego.
     */
    private Map<String, IComando> comandos;

    /**
     * Constructor que inicializa el estado de juego con el tablero del jugador,
     * su turno y el nombre del oponente.
     *
     * @param juego la referencia al juego principal
     * @param tableroJugador la vista del tablero del jugador
     * @param tuTurno indica si es el turno del jugador
     * @param nombreOponente el nombre del oponente
     */
    public EstadoJugar(Juego juego, VistaTablero tableroJugador, boolean tuTurno, String nombreOponente) {
        this.juego = juego;
        this.vista = new VistaJuego(juego.getPanel(), juego);
        this.vistaModelo = vista.getVistaModelo();

        // variables del juego
        vista.setTableroJugador(tableroJugador);
        vistaModelo.inicializarJuego(nombreOponente, tuTurno);
        inicializarComandos();
    }

    /**
     * Inicializa los comandos disponibles para manejar los mensajes en este
     * estado.
     */
    private void inicializarComandos() {
        comandos = new HashMap<>();
        comandos.put("ATACAR", new ComandoDisparar(this));
        comandos.put("RENDIRSE", new ComandoRendirse(this));
    }

    /**
     * Sale del estado de juego y quita los componentes de la vista.
     */
    @Override
    public void salir() {
        vista.quitarComponentes();
    }

    /**
     * Renderiza la vista de juego.
     *
     * @param g el objeto Graphics utilizado para dibujar la vista
     */
    @Override
    public void renderizar(Graphics g) {
        vista.dibujar(g);
    }

    /**
     * Maneja un mensaje recibido en el estado de juego.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    @Override
    public void handleMessage(Map<String, Object> mensaje) {
        if (mensaje == null) {
            return;
        }
        String accion = (String) mensaje.getOrDefault("accion", "default");
        if (accion == null) {
            System.err.println("Mensaje sin 'accion': " + mensaje);
            return;
        }

        IComando comando = comandos.get(accion);
        if (comando != null) {
            comando.execute(mensaje);
        } else {
            System.out.println("Acción desconocida en EstadoJuego: " + accion);
        }
    }

    /**
     * Maneja la respuesta de un ataque realizado durante el juego.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    public void handleAtacarResponse(Map<String, Object> mensaje) {
        vistaModelo.manejarAtaqueResponse(mensaje);

        // Verificar si el juego ha finalizado
        String resultado = (String) mensaje.get("resultado");
        if (resultado != null && resultado.equalsIgnoreCase("PARTIDA_FINALIZADA")) {
            // Obtener las estadísticas enviadas por el servidor
            Map<String, Object> estadisticas = (Map<String, Object>) mensaje.get("estadisticas");
            System.out.println("Se obtuvieron las estadisticas");
            this.vistaModelo.setEstadisticas(estadisticas);
            this.vistaModelo.setGanador((String) mensaje.get("ganador"));
            this.vistaModelo.setTiempoPartida((String) mensaje.get("tiempo_partida"));
        }
    }

    /**
     * Maneja la respuesta cuando un jugador se rinde durante el juego.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    public void handleRendirseResponse(Map<String, Object> mensaje) {
        String ganador = (String) mensaje.get("ganador");
        // Notificar al presentador que el juego ha terminado
        vistaModelo.finalizarJuegoPorRendicion(ganador);
    }

}
