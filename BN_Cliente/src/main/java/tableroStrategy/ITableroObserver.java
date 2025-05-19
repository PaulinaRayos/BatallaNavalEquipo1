/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tableroStrategy;

/**
 * Interfaz que define el comportamiento de un observador del tablero.
 *
 * Las clases que implementen esta interfaz serán notificadas cuando el tablero
 * sea actualizado para reaccionar a dichos cambios.
 *
 * @author pauli
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public interface ITableroObserver {

    /**
     * Método que se ejecuta cuando el tablero es actualizado.
     */
    void onTableroUpdated();
}
