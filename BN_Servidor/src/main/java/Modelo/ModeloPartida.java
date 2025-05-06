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
 *
 * @author pauli
 */
public class ModeloPartida {

    public static ModeloPartida instance;
    private List<ModeloJugador> jugadores = new ArrayList<>();
    private Map<String, ModeloTablero> tableros = new HashMap();
    private ModeloJugador ganador;
    private long tiempoInicio;
    private long tiempoFin;
    private EstadoPartida estado;
    private ModeloJugador jugadorTurno;
    private String codigoAcceso;

    private ModeloPartida() {
    }

    public static ModeloPartida getInstance() {
        if (instance == null) {
            instance = new ModeloPartida();
        }
        return instance;
    }

    private void actualizarTurno(ModeloJugador jugador) {
        this.jugadorTurno = jugador;
    }

    public static void setInstance(ModeloPartida instance) {
        ModeloPartida.instance = instance;
    }

    public Map<String, ModeloTablero> getTableros() {
        return tableros;
    }

    public void setTableros(Map<String, ModeloTablero> tableros) {
        this.tableros = tableros;
    }

    public void addTablero(String id, ModeloTablero tablero) {
        this.tableros.put(id, tablero);
    }

    public void ReiniciarPartida() {
        this.ganador = null;
        this.estado = null;
        this.jugadorTurno = null;
    }

    public void limpiarTableros() {
        this.tableros.forEach((k, tablero) -> {
            tablero.limpiarTablero();
        });
    }

    public ModeloJugador getGanador() {
        return ganador;
    }

    public void setGanador(ModeloJugador ganador) {
        this.ganador = ganador;
    }

    public ModeloTablero getTableroJugador(String id) {
        return this.tableros.getOrDefault(id, null);
    }

    public EstadoPartida getEstado() {
        return estado;
    }

    public void setEstado(EstadoPartida estado) {
        this.estado = estado;
    }

    public ModeloJugador getJugadorTurno() {
        return jugadorTurno;
    }

    public void setJugadorTurno(ModeloJugador jugadorTurno) {
        this.jugadorTurno = jugadorTurno;
    }

    public String getCodigoAcceso() {
        return codigoAcceso;
    }

    public void setCodigoAcceso(String codigoAcceso) {
        this.codigoAcceso = codigoAcceso;
    }

    public void addJugador(ModeloJugador jugador) {
        this.jugadores.add(jugador);
    }

    public List<ModeloJugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<ModeloJugador> jugadores) {
        this.jugadores = jugadores;
    }

    public boolean todosLosJugadoresListos() {
        for (ModeloJugador jugador : jugadores) {
            if (!jugador.isListo()) {
                return false;
            }
        }
        return true;
    }

    public synchronized void iniciarPartida() {
        this.tiempoInicio = System.currentTimeMillis();
        this.estado = EstadoPartida.EN_CURSO;
    }

    public synchronized void finalizarPartida() {
        this.tiempoFin = System.currentTimeMillis();
        this.estado = EstadoPartida.FINALIZADA;
    }

    public synchronized long getDuracion() {
        if (tiempoFin > 0 && tiempoInicio > 0) {
            return tiempoFin - tiempoInicio;
        } else {
            return 0;
        }
    }

    public void removerJugador(ModeloJugador jugador) {
        jugadores.remove(jugador);
        tableros.remove(jugador.getId());
    }

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
