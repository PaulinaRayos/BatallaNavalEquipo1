/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistaModelo;

import comunicacion.ConexionCliente;
import estados.EstadoBuscarPartida;
import estados.EstadoSalaEspera;
import interfaz.IVistaMenu;
import modelo.ModeloJugador;

/**
 * ViewModel para la vista del menú principal. Gestiona la interacción entre la
 * vista del menú, el modelo del jugador, la conexión con el servidor y el
 * control del flujo del juego.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class VistaModeloMenu {

    /**
     * Vista del menú principal.
     */
    private IVistaMenu vista;

    /**
     * Conexión con el servidor para las operaciones de red.
     */
    private ConexionCliente conexionCliente;

    /**
     * Modelo que contiene los datos del jugador actual.
     */
    private ModeloJugador jugador;

    /**
     * Referencia al juego principal para cambiar estados.
     */
    private Juego juego;

    /**
     * Constructor que inicializa el ViewModel con la vista del menú y la
     * referencia al juego principal.
     *
     * @param vista la vista del menú principal
     * @param juego la instancia del juego principal
     */
    public VistaModeloMenu(IVistaMenu vista, Juego juego) {
        this.vista = vista;
        this.conexionCliente = ConexionCliente.getInstance();
        this.jugador = ModeloJugador.getInstance();
        this.juego = juego;
    }

    /**
     * Crea una nueva partida solicitando al servidor la creación y cambia al
     * estado de sala de espera si el nombre del jugador es válido. Si el nombre
     * no es válido, muestra un mensaje de error en la vista.
     */
    public void crearPartida() {
        String nombreJugador = jugador.getNombre();
        if (nombreJugador == null || nombreJugador.isEmpty()) {
            vista.mostrarMensajeError("Nombre de jugador no válido");
            return;
        }
        conexionCliente.crearPartida(nombreJugador);
        avanzarACrearPartida();
    }

    /**
     * Cambia el estado del juego a sala de espera para crear la partida.
     */
    public void avanzarACrearPartida() {
        juego.cambiarEstado(new EstadoSalaEspera(juego));
    }

    /**
     * Cambia el estado del juego a búsqueda de partidas para unirse a una
     * existente.
     */
    public void avanzarAUnirseAPartida() {
        juego.cambiarEstado(new EstadoBuscarPartida(juego));
    }
}
