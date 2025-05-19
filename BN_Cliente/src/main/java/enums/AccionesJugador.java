/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enums;

/**
 * Enumeración que representa las posibles acciones que un jugador puede
 * realizar durante el juego.
 *
 * Estas acciones abarcan desde interacciones básicas como atacar, hasta
 * controles de flujo de partida y decisiones relacionadas con el reinicio o
 * abandono del juego.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public enum AccionesJugador {

    /**
     * Acción para atacar al oponente.
     */
    ATACAR,
    /**
     * Acción para ordenar las piezas o unidades en el tablero.
     */
    ORDENAR,
    /**
     * Acción para crear una nueva partida.
     */
    CREAR_PARTIDA,
    /**
     * Acción para unirse a una partida existente.
     */
    UNIRSE_PARTIDA,
    /**
     * Acción que indica que el jugador está listo para iniciar la partida.
     */
    JUGADOR_LISTO,
    /**
     * Acción para rendirse durante una partida.
     */
    RENDIRSE,
    /**
     * Acción para solicitar o mostrar estadísticas de juego.
     */
    ESTADISTICAS,
    /**
     * Acción para solicitar volver a jugar una nueva partida.
     */
    VOLVER_A_JUGAR,
    /**
     * Respuesta del jugador a la solicitud de volver a jugar.
     */
    RESPUESTA_VOLVER_A_JUGAR,
    /**
     * Notificación de que el oponente desea volver a jugar.
     */
    OPONENTE_QUIERE_VOLVER_A_JUGAR,
    /**
     * Notificación de que el oponente no desea volver a jugar.
     */
    OPONENTE_NO_QUIERE_VOLVER_A_JUGAR,
    /**
     * Acción para iniciar formalmente la partida.
     */
    INICIAR_PARTIDA,
    /**
     * Acción para salir del juego o cerrar la sesión.
     */
    SALIR;
}
