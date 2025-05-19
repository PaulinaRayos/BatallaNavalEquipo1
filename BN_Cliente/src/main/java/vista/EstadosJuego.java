/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 * Enumeración que define los diferentes estados en los que puede estar el
 * juego.
 *
 * Los estados incluyen: - BIENVENIDA: Pantalla inicial de bienvenida. - MENU:
 * Menú principal. - ORGANIZAR: Estado para organizar la partida o componentes.
 * - INSTRUCCIONES: Pantalla con instrucciones del juego. - SALA_ESPERA: Sala de
 * espera antes de iniciar la partida. - BUSCAR_PARTIDA: Estado para buscar
 * partidas existentes. - JUGAR: Estado activo de juego.
 *
 * Además, contiene una variable estática que mantiene el estado actual del
 * juego.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public enum EstadosJuego {
    BIENVENIDA, MENU, ORGANIZAR, INSTRUCCIONES, SALA_ESPERA, BUSCAR_PARTIDA, JUGAR;

    /**
     * Variable estática que representa el estado actual del juego.
     */
    public static EstadosJuego estado = BIENVENIDA;
}
