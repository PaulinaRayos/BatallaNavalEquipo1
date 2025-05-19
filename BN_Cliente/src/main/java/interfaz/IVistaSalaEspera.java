/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaz;

import vistaModelo.VistaModeloSalaEspera;

/**
 * Interfaz que define el comportamiento de la vista de sala de espera.
 *
 * Esta vista permite gestionar a los jugadores que se han unido a la partida,
 * mostrar el código de acceso y controlar la navegación entre vistas.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public interface IVistaSalaEspera {

    /**
     * Muestra el código de acceso para la partida en la interfaz de sala de
     * espera.
     *
     * @param codigoAcceso el código de acceso de la partida
     */
    void mostrarCodigoAcceso(String codigoAcceso);

    /**
     * Agrega o actualiza el estado de un jugador en la lista de jugadores.
     *
     * @param nombreJugador el nombre del jugador
     * @param listo indica si el jugador está listo
     */
    void agregarOActualizarJugador(String nombreJugador, boolean listo);

    /**
     * Limpia la lista de jugadores en la interfaz.
     */
    void limpiarListaJugadores();

    /**
     * Bloquea el botón de continuar en la interfaz de sala de espera.
     */
    void bloquearBotonContinuar();

    /**
     * Muestra un mensaje en la interfaz de sala de espera.
     *
     * @param mensaje el mensaje a mostrar
     */
    void mostrarMensaje(String mensaje);

    /**
     * Navega al menú principal.
     */
    void navegarAMenu();

    /**
     * Navega a la vista de organización de naves.
     */
    void navegarAOrganizar();

    /**
     * Verifica si el jugador está listo.
     *
     * @return true si el jugador está listo, false en caso contrario
     */
    boolean isEstoyListo();

    /**
     * Establece si el jugador está listo.
     *
     * @param listo true si el jugador está listo, false en caso contrario
     */
    void setEstoyListo(boolean listo);

    /**
     * Obtiene la vista modelo asociada a la vista de sala de espera.
     *
     * @return la vista modelo de la vista de sala de espera
     */
    VistaModeloSalaEspera getVistaModelo();
}
