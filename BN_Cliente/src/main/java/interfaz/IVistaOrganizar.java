/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaz;

import java.awt.Color;
import vista.VistaTablero;

/**
 * Interfaz que define el comportamiento de la vista de organización de naves.
 *
 * Esta vista permite al jugador organizar sus naves antes de iniciar la
 * partida. Ofrece métodos para bloquear la interfaz, actualizar la
 * visualización y navegar al siguiente estado del juego.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public interface IVistaOrganizar {

    /**
     * Muestra un mensaje indicando que un jugador está esperando.
     *
     * @param nombreJugador el nombre del jugador que está esperando
     */
    void mostrarMensajeJugadorEsperando(String nombreJugador);

    /**
     * Obtiene la vista del tablero del jugador.
     *
     * @return la vista del tablero del jugador
     */
    VistaTablero getTablero();

    /**
     * Muestra un mensaje en la interfaz de organización.
     *
     * @param mensaje el mensaje a mostrar
     */
    void mostrarMensaje(String mensaje);

    /**
     * Navega a la vista de juego.
     */
    void navegarAJugar();

    /**
     * Bloquea la interacción del jugador con la interfaz de organización.
     */
    void bloquearInterfaz();

    /**
     * Actualiza el color de los paneles laterales en la interfaz.
     *
     * @param nuevoColorNave el nuevo color de las naves
     */
    void actualizarColorPanelesLaterales(Color nuevoColorNave);
}
