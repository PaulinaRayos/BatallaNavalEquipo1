/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enums;

/**
 * Enumeración que representa los posibles resultados de un disparo en el juego.
 *
 * Se incluyen resultados tanto para ataques realizados como para ataques
 * recibidos, y cada uno puede haber sido un impacto o un fallo.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public enum ResultadosDisparos {

    /**
     * Resultado que indica que el ataque realizado por el jugador fue un
     * impacto exitoso.
     */
    RESULTADO_ATAQUE_REALIZADO_IMPACTO,
    /**
     * Resultado que indica que el jugador ha recibido un ataque que fue un
     * impacto.
     */
    RESULTADO_ATAQUE_RECIBIDO_IMPACTO,
    /**
     * Resultado que indica que el ataque realizado por el jugador falló.
     */
    RESULTADO_ATAQUE_REALIZADO_FALLO,
    /**
     * Resultado que indica que el jugador ha recibido un ataque que falló.
     */
    RESULTADO_ATAQUE_RECIBIDO_FALLO,
}
