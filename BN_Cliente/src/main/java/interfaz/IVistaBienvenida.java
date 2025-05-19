/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaz;

/**
 * Interfaz que define el comportamiento de la vista de bienvenida.
 *
 * Contiene métodos para mostrar mensajes de error y obtener el nombre del
 * jugador ingresado.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public interface IVistaBienvenida {

    /**
     * Muestra un mensaje de error en la interfaz de bienvenida.
     *
     * @param mensaje el mensaje de error a mostrar
     */
    public void mostrarMensajeError(String mensaje);

    /**
     * Obtiene el nombre del jugador ingresado en la vista de bienvenida.
     *
     * @return el nombre del jugador
     */
    public String obtenerNombreJugador();

}
