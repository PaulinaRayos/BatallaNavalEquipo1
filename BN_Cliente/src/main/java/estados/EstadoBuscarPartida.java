/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estados;

import comunicacion.ComandoUnirsePartida;
import comunicacion.IComando;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.ModeloJugador;
import vista.VistaBuscarPartida;
import vistaModelo.Juego;
import vistaModelo.VistaModeloBuscarPartida;

/**
 * Estado del juego que representa la pantalla de búsqueda de partida, donde el
 * jugador puede unirse a una partida existente.
 *
 * Este estado maneja la lógica de unión y transición a la sala de espera.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class EstadoBuscarPartida implements IEstado {

    /**
     * Referencia al juego principal.
     */
    private Juego juego;

    /**
     * Vista que representa la interfaz de búsqueda de partida.
     */
    private VistaBuscarPartida vista;

    /**
     * VistaModelo asociado a la vista de búsqueda de partida.
     */
    private VistaModeloBuscarPartida vistaModelo;

    /**
     * Mapa que contiene los comandos disponibles en el estado de búsqueda de
     * partida.
     */
    private Map<String, IComando> comandos;

    /**
     * Constructor que inicializa el estado de búsqueda de partida con el juego
     * especificado.
     *
     * @param juego la referencia al juego principal
     */
    public EstadoBuscarPartida(Juego juego) {
        this.juego = juego;
        this.vista = new VistaBuscarPartida(juego.getPanel(), juego);
        this.vistaModelo = vista.getPresentador();
        inicializarComandos();
    }

    /**
     * Inicializa los comandos disponibles para manejar los mensajes en este
     * estado.
     */
    private void inicializarComandos() {
        comandos = new HashMap<>();
        comandos.put("UNIRSE_PARTIDA", new ComandoUnirsePartida(this));
    }

    /**
     * Sale del estado de búsqueda de partida y quita los componentes de la
     * vista.
     */
    @Override
    public void salir() {
        vista.quitarComponentes();
    }

    /**
     * Renderiza la vista de búsqueda de partida.
     *
     * @param g el objeto Graphics utilizado para dibujar la vista
     */
    @Override
    public void renderizar(Graphics g) {
        vista.dibujar(g);
    }

    /**
     * Maneja un mensaje recibido en el estado de búsqueda de partida.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    @Override
    public void handleMessage(Map<String, Object> mensaje) {
        String accion = (String) mensaje.get("accion");
        if (accion == null) {
            System.err.println("Mensaje sin 'accion': " + mensaje);
            return;
        }

        IComando comando = comandos.get(accion);
        if (comando != null) {
            comando.execute(mensaje);
        } else {
            System.out.println("Acción desconocida en EstadoBuscarPartida: " + accion);
        }
    }

    /**
     * Maneja la respuesta para unirse a una partida.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    public void handleUnirsePartidaResponse(Map<String, Object> mensaje) {
        if (mensaje.containsKey("error")) {
            String error = (String) mensaje.get("error");
            vista.mostrarMensaje(error);
        } else {
            String idJugador = (String) mensaje.get("id");
            String codigoAcceso = (String) mensaje.get("codigo_acceso");
            List<String> nombresJugadores = (List<String>) mensaje.get("nombres_jugadores");

            ModeloJugador jugador = ModeloJugador.getInstance();
            jugador.setId(idJugador);

            vista.quitarComponentes();
            juego.cambiarEstado(new EstadoSalaEspera(juego, codigoAcceso, nombresJugadores));
        }
    }
}
