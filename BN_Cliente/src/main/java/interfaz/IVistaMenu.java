/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaz;

/**
 * Interfaz que define el comportamiento de la vista del menú principal.
 *
 * Proporciona métodos para mostrar mensajes, como mensajes de error.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public interface IVistaMenu {

    /**
     * Muestra un mensaje de error en la interfaz del menú.
     *
     * @param mensaje el mensaje de error a mostrar
     */
    public void mostrarMensajeError(String mensaje);
}
