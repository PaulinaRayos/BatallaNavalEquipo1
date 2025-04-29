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
 *
 * @author pauli
 */
public class VistaModeloMenu {
    /**
     * Vista del menú.
     */
    private IVistaMenu vista;
    
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
     * Constructor que inicializa el presentador con la vista y el juego especificados.
     *
     * @param vista la vista del menú
     * @param juego la referencia al juego principal
     */
    public VistaModeloMenu(IVistaMenu vista, Juego juego) {
        this.vista = vista;
        this.conexionCliente = ConexionCliente.getInstance();
        this.jugador = ModeloJugador.getInstance();
        this.juego = juego;
    }

    /**
     * Crea una nueva partida y cambia al estado correspondiente.
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
     * Cambia al estado de creación de partida (sala de espera).
     */
    public void avanzarACrearPartida() {
        juego.cambiarEstado(new EstadoSalaEspera(juego));
    }
    
    /**
     * Cambia al estado de unión a una partida existente.
     */
    public void avanzarAUnirseAPartida() {
        juego.cambiarEstado(new EstadoBuscarPartida(juego));
    }
    
    
}
