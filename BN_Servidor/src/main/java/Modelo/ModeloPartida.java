/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import enums.EstadoPartida;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que representa una partida del juego. Implementa el patrón Singleton.
 * Administra jugadores, tableros, estado de la partida y control del turno.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ModeloPartida {

    /**
     * Instancia única de la clase ModeloPartida (Singleton).
     */
    public static ModeloPartida instance;

    /**
     * Lista de jugadores en la partida.
     */
    private List<ModeloJugador> jugadores = new ArrayList<>();

    /**
     * Mapa de tableros asociados a los jugadores por su ID.
     */
    private Map<String, ModeloTablero> tableros = new HashMap();

    /**
     * Jugador que ha ganado la partida.
     */
    private ModeloJugador ganador;

    /**
     * Tiempo en milisegundos en que inició la partida.
     */
    private long tiempoInicio;

    /**
     * Tiempo en milisegundos en que finalizó la partida.
     */
    private long tiempoFin;

    /**
     * Estado actual de la partida.
     */
    private EstadoPartida estado;

    /**
     * Jugador que tiene el turno actual.
     */
    private ModeloJugador jugadorTurno;

    /**
     * Código de acceso de la partida.
     */
    private String codigoAcceso;

    /**
     * Constructor privado (patrón Singleton).
     */
    private ModeloPartida() {
    }

    /**
     * Devuelve la instancia única de la partida.
     *
     * @return instancia única de ModeloPartida
     */
    public static ModeloPartida getInstance() {
        if (instance == null) {
            instance = new ModeloPartida();
        }
        return instance;
    }

    /**
     * Actualiza el jugador en turno.
     *
     * @param jugador jugador al que se le asigna el turno
     */
    private void actualizarTurno(ModeloJugador jugador) {
        this.jugadorTurno = jugador;
    }

    /**
     * Establece una nueva instancia de partida (para propósitos de reinicio o
     * carga).
     *
     * @param instance nueva instancia de ModeloPartida
     */
    public static void setInstance(ModeloPartida instance) {
        ModeloPartida.instance = instance;
    }

    /**
     * Devuelve el mapa de tableros.
     *
     * @return mapa de tableros
     */
    public Map<String, ModeloTablero> getTableros() {
        return tableros;
    }

    /**
     * Establece el mapa de tableros.
     *
     * @param tableros nuevo mapa de tableros
     */
    public void setTableros(Map<String, ModeloTablero> tableros) {
        this.tableros = tableros;
    }

    /**
     * Agrega un tablero asociado a un ID de jugador.
     *
     * @param id ID del jugador
     * @param tablero tablero correspondiente
     */
    public void addTablero(String id, ModeloTablero tablero) {
        this.tableros.put(id, tablero);
    }

    /**
     * Reinicia los atributos de estado de la partida.
     */
    public void ReiniciarPartida() {
        this.ganador = null;
        this.estado = null;
        this.jugadorTurno = null;
    }

    /**
     * Limpia los tableros de todos los jugadores.
     */
    public void limpiarTableros() {
        this.tableros.forEach((k, tablero) -> {
            tablero.limpiarTablero();
        });
    }

    /**
     * Devuelve el jugador ganador.
     *
     * @return jugador que ganó la partida
     */
    public ModeloJugador getGanador() {
        return ganador;
    }

    /**
     * Establece el jugador ganador.
     *
     * @param ganador jugador ganador
     */
    public void setGanador(ModeloJugador ganador) {
        this.ganador = ganador;
    }

    /**
     * Devuelve el tablero asociado a un jugador por su ID.
     *
     * @param id ID del jugador
     * @return tablero correspondiente o null si no existe
     */
    public ModeloTablero getTableroJugador(String id) {
        return this.tableros.getOrDefault(id, null);
    }

    /**
     * Devuelve el estado actual de la partida.
     *
     * @return estado de la partida
     */
    public EstadoPartida getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la partida.
     *
     * @param estado nuevo estado
     */
    public void setEstado(EstadoPartida estado) {
        this.estado = estado;
    }

    /**
     * Devuelve el jugador que tiene el turno actual.
     *
     * @return jugador en turno
     */
    public ModeloJugador getJugadorTurno() {
        return jugadorTurno;
    }

    /**
     * Establece el jugador que tiene el turno.
     *
     * @param jugadorTurno jugador en turno
     */
    public void setJugadorTurno(ModeloJugador jugadorTurno) {
        this.jugadorTurno = jugadorTurno;
    }

    /**
     * Devuelve el código de acceso de la partida.
     *
     * @return código de acceso
     */
    public String getCodigoAcceso() {
        return codigoAcceso;
    }

    /**
     * Establece el código de acceso de la partida.
     *
     * @param codigoAcceso nuevo código
     */
    public void setCodigoAcceso(String codigoAcceso) {
        this.codigoAcceso = codigoAcceso;
    }

    /**
     * Agrega un jugador a la partida.
     *
     * @param jugador jugador a agregar
     */
    public void addJugador(ModeloJugador jugador) {
        this.jugadores.add(jugador);
    }

    /**
     * Devuelve la lista de jugadores de la partida.
     *
     * @return lista de jugadores
     */
    public List<ModeloJugador> getJugadores() {
        return jugadores;
    }

    /**
     * Establece la lista de jugadores.
     *
     * @param jugadores lista de jugadores
     */
    public void setJugadores(List<ModeloJugador> jugadores) {
        this.jugadores = jugadores;
    }

    /**
     * Verifica si todos los jugadores están listos para comenzar la partida.
     *
     * @return true si todos están listos, false en caso contrario
     */
    public boolean todosLosJugadoresListos() {
        for (ModeloJugador jugador : jugadores) {
            if (!jugador.isListo()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Marca el inicio de la partida, registrando el tiempo y cambiando el
     * estado.
     */
    public synchronized void iniciarPartida() {
        this.tiempoInicio = System.currentTimeMillis();
        this.estado = EstadoPartida.EN_CURSO;
    }

    /**
     * Marca el fin de la partida, registrando el tiempo y cambiando el estado.
     */
    public synchronized void finalizarPartida() {
        this.tiempoFin = System.currentTimeMillis();
        this.estado = EstadoPartida.FINALIZADA;
    }

    /**
     * Devuelve la duración total de la partida en milisegundos.
     *
     * @return duración en milisegundos
     */
    public synchronized long getDuracion() {
        if (tiempoFin > 0 && tiempoInicio > 0) {
            return tiempoFin - tiempoInicio;
        } else {
            return 0;
        }
    }

    /**
     * Remueve un jugador y su tablero de la partida.
     *
     * @param jugador jugador a remover
     */
    public void removerJugador(ModeloJugador jugador) {
        jugadores.remove(jugador);
        tableros.remove(jugador.getId());
    }

    /**
     * Verifica si todos los jugadores han solicitado revancha.
     *
     * @return true si ambos quieren revancha, false en caso contrario
     */
    public boolean ambosQuierenRevancha() {
        if (jugadores.size() < 2) {
            return false;
        }
        for (ModeloJugador jugador : jugadores) {
            if (!jugador.isQuiereRevancha()) {
                return false;
            }
        }
        return true;
    }

}
