/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author pauli
 */
public enum EstadosJuego {
    BIENVENIDA,MENU,ORGANIZAR,INSTRUCCIONES,SALA_ESPERA,BUSCAR_PARTIDA, JUGAR;
    
    /**
     * Variable estática que representa el estado actual del juego.
     */
    public static EstadosJuego estado = BIENVENIDA;
}
