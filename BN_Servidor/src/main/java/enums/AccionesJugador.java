/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enums;

/**
 * Enumeración que define las posibles acciones que un jugador puede realizar
 * durante el juego. Incluye acciones como atacar, ordenar unidades, crear o
 * unirse a una partida, indicar que está listo, rendirse, consultar
 * estadísticas, solicitar revancha, responder a solicitudes de revancha,
 * iniciar partida y salir.
 *
 * Valores: - ATACAR: Acción de atacar a un oponente. - ORDENAR: Ordenar
 * unidades en el tablero. - CREAR_PARTIDA: Crear una nueva partida. -
 * UNIRSE_PARTIDA: Unirse a una partida existente. - JUGADOR_LISTO: Indicar que
 * el jugador está listo para comenzar. - RENDIRSE: Rendirse en la partida. -
 * ESTADISTICAS: Consultar estadísticas de juego. - VOLVER_A_JUGAR: Solicitar
 * una revancha. - RESPUESTA_VOLVER_A_JUGAR: Responder a la solicitud de
 * revancha. - OPONENTE_QUIERE_VOLVER_A_JUGAR: Notificar que el oponente quiere
 * revancha. - OPONENTE_NO_QUIERE_VOLVER_A_JUGAR: Notificar que el oponente no
 * quiere revancha. - INICIAR_PARTIDA: Iniciar la partida. - SALIR: Salir del
 * juego o de la sesión.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public enum AccionesJugador {
    ATACAR,
    ORDENAR,
    CREAR_PARTIDA,
    UNIRSE_PARTIDA,
    JUGADOR_LISTO,
    RENDIRSE,
    ESTADISTICAS,
    VOLVER_A_JUGAR,
    RESPUESTA_VOLVER_A_JUGAR,
    OPONENTE_QUIERE_VOLVER_A_JUGAR,
    OPONENTE_NO_QUIERE_VOLVER_A_JUGAR,
    INICIAR_PARTIDA,
    SALIR;
}
