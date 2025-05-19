/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estados;

/**
 * Enumeración que representa el estado posible de una casilla en el tablero del
 * juego.
 *
 * Puede tener uno de los siguientes valores: - OCUPADA: la casilla contiene
 * parte de una nave. - VACIA: la casilla está vacía y no ha sido impactada. -
 * IMPACTADA: la casilla ha sido atacada.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public enum EstadoCasilla {
    OCUPADA,
    VACIA,
    IMPACTADA
}
