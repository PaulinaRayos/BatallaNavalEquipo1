/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaz;

import vistaModelo.VistaModeloBuscarPartida;

/**
 * Interfaz que define el comportamiento de la vista de búsqueda de partida.
 *
 * Permite mostrar mensajes, obtener el código de acceso, navegar entre vistas,
 * y acceder al presentador asociado.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public interface IVistaBuscarPartida {

    /**
     * Muestra un mensaje en la interfaz de búsqueda de partida.
     *
     * @param mensaje el mensaje a mostrar
     */
    void mostrarMensaje(String mensaje);

    /**
     * Obtiene el código de acceso ingresado en la vista de búsqueda de partida.
     *
     * @return el código de acceso
     */
    String obtenerCodigoAcceso();

    /**
     * Navega a la vista de sala de espera.
     */
    void navegarASalaDeEspera();

    /**
     * Navega al menú principal.
     */
    void navegarAMenu();

    /**
     * Obtiene el presentador asociado a la vista de búsqueda de partida.
     *
     * @return el presentador de la vista
     */
    VistaModeloBuscarPartida getPresentador();

}
