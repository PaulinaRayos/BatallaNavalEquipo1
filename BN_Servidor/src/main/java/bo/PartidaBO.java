/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import Modelo.ModeloJugador;
import Modelo.ModeloPartida;
import comunicacion.ClientManager;
import convertidor.toJSON;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import comunicacion.MessageUtil;
import java.net.Socket;
import java.util.Random;

/**
 *
 * @author pauli
 */
public class PartidaBO {
    
    private ModeloPartida partida;

    public PartidaBO() {
        this.partida = ModeloPartida.getInstance();
    }

    // Método para crear una partida
    public Map<String, Object> crearPartida(Map<String, Object> data, String clientId) {
        // Verifica si el cliente ya está registrado
        if (ClientManager.getClientSocket(clientId) == null) {
            throw new IllegalStateException("El cliente no está conectado.");
        }

        String codigoAcceso = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
        partida.setCodigoAcceso(codigoAcceso);
        System.out.println("El codigo de acceso es: " + codigoAcceso);
        ModeloJugador host = crearJugador(clientId, (String) data.get("nombre"));
        partida.addJugador(host);
        // se habla al metodo para iniciar su tablero y unidades
        ClientManager.addClient(ClientManager.getClientSocket(clientId), clientId, host);
        return toJSON.dataToJSON("accion", "CREAR_PARTIDA", "id", host.getId(), "codigo_acceso", codigoAcceso);
    }

    // Método para que un jugador se una a una partida
    public Map<String, Object> unirsePartida(Map<String, Object> data, String clientId) {
        String codigo_acceso = (String) data.get("codigo_acceso");
        System.out.println(codigo_acceso);
        if (!partida.getCodigoAcceso().equalsIgnoreCase(codigo_acceso)) {
            return toJSON.dataToJSON("accion", "UNIRSE_PARTIDA", "error", "El código de acceso no coincide");
        }
        ModeloJugador jugador = crearJugador(clientId, (String) data.get("nombre"));
        partida.addJugador(jugador);
        // se habla al metodo para iniciar su tablero y unidades
        ClientManager.addClient(ClientManager.getClientSocket(clientId), clientId, jugador);
        partida.getJugadores().stream().forEach(p -> System.out.println("Jugador en la partida " + p.getId()));
        List<String> nombresJugadores = partida.getJugadores().stream()
                .map(ModeloJugador::getNombre)
                .collect(Collectors.toList());

        // Notificar a todos los jugadores que un nuevo jugador se ha unido
        notificarNuevoJugador(jugador);

        return toJSON.dataToJSON(
                "accion", "UNIRSE_PARTIDA",
                "id", jugador.getId(),
                "codigo_acceso", codigo_acceso,
                "nombres_jugadores", nombresJugadores);
    }

    private void notificarNuevoJugador(ModeloJugador nuevoJugador) {
        for (ModeloJugador jugadorExistente : partida.getJugadores()) {
            if (!jugadorExistente.getId().equals(nuevoJugador.getId())) {
                String clientIdExistente = jugadorExistente.getId();
                Socket socketJugador = ClientManager.getClientSocket(clientIdExistente);

                Map<String, Object> mensajeNotificacion = toJSON.dataToJSON(
                        "accion", "NUEVO_JUGADOR",
                        "nombre_jugador", nuevoJugador.getNombre());

                MessageUtil.enviarMensaje(socketJugador, mensajeNotificacion);
            }
        }
    }

    public ModeloJugador crearJugador(String id, String nombre) {
        return new ModeloJugador(id, nombre);
    }

    public Map<String, Object> jugadorListo(Map<String, Object> request, String clientId) {
        ModeloJugador jugador = ClientManager.getJugadorByClientId(clientId);
        if (jugador != null) {
            jugador.setListo(true);

            // Notificar a todos los jugadores que este jugador está listo
            notificarEstadoListo(jugador);

            // Si todos los jugadores están listos, notificar para avanzar
            if (partida.todosLosJugadoresListos()) {
                // Reiniciar el estado listo de los jugadores para la siguiente fase
                reiniciarEstadoListo();

                // Notificar a los jugadores para iniciar la organización
                notificarIniciarOrganizar();
            }
        }
        return null;
    }

    private void notificarEstadoListo(ModeloJugador jugadorListo) {
        for (ModeloJugador jugador : partida.getJugadores()) {
            Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
            Map<String, Object> mensaje = toJSON.dataToJSON(
                    "accion", "ACTUALIZAR_ESTADO_LISTO",
                    "id_jugador", jugadorListo.getId(),
                    "nombre_jugador", jugadorListo.getNombre(),
                    "listo", jugadorListo.isListo());
            MessageUtil.enviarMensaje(socketJugador, mensaje);
        }
    }

    private void notificarTodosListos() {
        for (ModeloJugador jugador : partida.getJugadores()) {
            Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
            Map<String, Object> mensaje = toJSON.dataToJSON(
                    "accion", "TODOS_LISTOS");
            MessageUtil.enviarMensaje(socketJugador, mensaje);

        }
    }

    private void notificarIniciarOrganizar() {
        for (ModeloJugador jugador : partida.getJugadores()) {
            Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
            Map<String, Object> mensaje = toJSON.dataToJSON(
                    "accion", "INICIAR_ORGANIZAR");
            MessageUtil.enviarMensaje(socketJugador, mensaje);
        }
    }
    
    public void reiniciarEstadoListo() {
        for (ModeloJugador jugador : partida.getJugadores()) {
            jugador.setListo(false);
        }
    }
    
    private void determinarJugadorInicial() {
        List<ModeloJugador> jugadores = partida.getJugadores();
        Random random = new Random();
        int indice = random.nextInt(jugadores.size());
        ModeloJugador jugadorInicial = jugadores.get(indice);
        partida.setJugadorTurno(jugadorInicial);
    }

    private void notificarJugadorEsperando(ModeloJugador jugadorListo) {
        for (ModeloJugador jugador : partida.getJugadores()) {
            if (!jugador.getId().equals(jugadorListo.getId())) {
                Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
                Map<String, Object> mensaje = toJSON.dataToJSON(
                        "accion", "JUGADOR_ESPERANDO",
                        "nombre_jugador", jugadorListo.getNombre());
                MessageUtil.enviarMensaje(socketJugador, mensaje);
            }
        }
    }
 public void reiniciarPartida() {
        partida.ReiniciarPartida();
    }

    public void finalizarPartida() {
        ModeloPartida.instance = null;
    }

    private String formatoDuracion(long duracionMilisegundos) {
        long segundos = duracionMilisegundos / 1000;
        long minutos = segundos / 60;
        segundos = segundos % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }
    
    
    }
