/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistaModelo;

import comunicacion.ConexionCliente;
import estados.EstadoMenu;
import interfaz.IVistaBuscarPartida;
import java.util.Map;
import modelo.ModeloJugador;

/**
 * ViewModel para la vista de búsqueda de partida. Gestiona la lógica para
 * unirse a partidas existentes y la navegación hacia el menú principal.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class VistaModeloBuscarPartida {

    /**
     * Vista de búsqueda de partida.
     */
    private IVistaBuscarPartida vista;

    /**
     * Conexión con el servidor.
     */
    private ConexionCliente conexionCliente;

    /**
     * Modelo del jugador.
     */
    private ModeloJugador jugador;

    /**
     * Referencia al juego principal.
     */
    private Juego juego;

    /**
     * Constructor que inicializa el ViewModel con la vista y el juego.
     *
     * @param vista la vista de búsqueda de partida
     * @param juego la instancia principal del juego
     */
    public VistaModeloBuscarPartida(IVistaBuscarPartida vista, Juego juego) {
        this.vista = vista;
        this.conexionCliente = ConexionCliente.getInstance();
        this.jugador = ModeloJugador.getInstance();
        this.juego = juego;
    }

    /**
     * Intenta unirse a una partida usando el código de acceso proporcionado en
     * la vista. Si el código está vacío, muestra un mensaje de error.
     */
    public void unirseAPartida() {
        String codigoAcceso = vista.obtenerCodigoAcceso();
        if (codigoAcceso.isEmpty()) {
            vista.mostrarMensaje("Por favor, ingresa el código de la sala.");
            return;
        }
        String nombreJugador = jugador.getNombre();
        conexionCliente.unirsePartida(codigoAcceso, nombreJugador);
    }

    /**
     * Cambia el estado del juego para regresar al menú principal.
     */
    public void regresarAlMenu() {
        juego.cambiarEstado(new EstadoMenu(juego));
    }

    /**
     * Maneja la respuesta recibida al intentar unirse a una partida. Si hay un
     * error, muestra el mensaje correspondiente en la vista.
     *
     * @param mensaje el mensaje recibido del servidor
     */
    public void manejarRespuestaUnirsePartida(Map<String, Object> mensaje) {
        if (mensaje.containsKey("error")) {
            String error = (String) mensaje.get("error");
            vista.mostrarMensaje(error);
        }
    }

}
