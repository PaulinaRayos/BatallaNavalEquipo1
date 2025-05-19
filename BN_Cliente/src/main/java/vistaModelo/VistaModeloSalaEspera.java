/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistaModelo;

import comunicacion.ConexionCliente;
import interfaz.IVistaSalaEspera;
import java.util.List;
import modelo.ModeloJugador;

/**
 * Clase que funciona como el ViewModel de la sala de espera. Gestiona la
 * comunicación entre la vista y el modelo para la sala de espera del juego.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class VistaModeloSalaEspera {

    /**
     * Vista de sala de espera.
     */
    private IVistaSalaEspera vista;

    /**
     * Conexión con el servidor.
     */
    private ConexionCliente conexionCliente;

    /**
     * Modelo del jugador.
     */
    private ModeloJugador jugador;

    /**
     * Constructor que inicializa el ViewModel con la vista especificada.
     * También inicializa la conexión con el servidor y el modelo del jugador.
     *
     * @param vista la vista de sala de espera que será gestionada
     */
    public VistaModeloSalaEspera(IVistaSalaEspera vista) {
        this.vista = vista;
        this.conexionCliente = ConexionCliente.getInstance();
        this.jugador = ModeloJugador.getInstance();
    }

    /**
     * Inicia la sala de espera, mostrando el código de acceso y la lista de
     * jugadores.
     */
    public void iniciar() {
        // Aquí se podría obtener el código de acceso y el ID del jugador si es necesario
        // vista.mostrarCodigoAcceso(codigoAcceso);
        // Inicializar lista de jugadores si es necesario
    }

    /**
     * Marca al jugador como listo y envía la notificación al servidor. Si el
     * jugador no está marcado como listo, lo marca y bloquea el botón de
     * continuar.
     */
    public void jugadorListo() {
        if (!vista.isEstoyListo()) {
            vista.setEstoyListo(true);
            vista.bloquearBotonContinuar();
            conexionCliente.jugadorListo();
        }
    }

    /**
     * Sale de la sala de espera y regresa al menú principal.
     */
    public void salir() {
        vista.navegarAMenu();
    }

    /**
     * Agrega o actualiza un jugador en la lista de la sala de espera.
     *
     * @param nombreJugador el nombre del jugador a agregar o actualizar
     * @param listo indica si el jugador está listo o no
     */
    public void agregarOActualizarJugador(String nombreJugador, boolean listo) {
        vista.agregarOActualizarJugador(nombreJugador, listo);
    }

    /**
     * Limpia la lista de jugadores en la vista de la sala de espera.
     */
    public void limpiarListaJugadores() {
        vista.limpiarListaJugadores();
    }

    /**
     * Inicia la organización de las unidades cambiando al estado
     * correspondiente.
     */
    public void iniciarOrganizar() {
        vista.navegarAOrganizar();
    }

    /**
     * Actualiza la lista de jugadores en la vista de la sala de espera. Limpia
     * la lista y agrega a todos los jugadores con el estado no listo (false).
     *
     * @param nombresJugadores la lista de nombres de los jugadores a mostrar
     */
    public void actualizarListaJugadores(List<String> nombresJugadores) {
        vista.limpiarListaJugadores();
        for (String nombreJugador : nombresJugadores) {
            vista.agregarOActualizarJugador(nombreJugador, false);
        }
    }

    /**
     * Maneja la actualización del estado "listo" de un jugador específico.
     *
     * @param nombreJugador el nombre del jugador a actualizar
     * @param listo indica si el jugador está listo o no
     */
    public void manejarActualizarEstadoListo(String nombreJugador, boolean listo) {
        vista.agregarOActualizarJugador(nombreJugador, listo);
    }

    /**
     * Maneja el evento cuando todos los jugadores están listos. Inicia la
     * navegación hacia la pantalla de organización de unidades.
     */
    public void manejarTodosListos() {
        vista.navegarAOrganizar();
    }

}
