/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import Modelo.ModeloJugador;
import bo.PartidaBO;
import enums.AccionesJugador;
import enums.ControlPartida;
import eventos.EventBus;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

/**
 * Clase responsable de manejar las acciones recibidas desde los clientes y
 * delegar la lógica de negocio relacionada con las partidas del juego.
 * Implementa el patrón Singleton para garantizar una única instancia.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class HandlerActions {

    /**
     * Componente que contiene la lógica de negocio para la gestión de partidas.
     */
    private PartidaBO partidaBO;

    /**
     * Instancia estática única de HandlerActions para el patrón Singleton.
     */
    private static HandlerActions instance = null;

    /**
     * Constructor privado para evitar la creación directa de instancias.
     * Inicializa la lógica de negocio para partidas.
     */
    private HandlerActions() {
        this.partidaBO = new PartidaBO();
    }

    /**
     * Devuelve la instancia única de HandlerActions. Si no existe, la crea y la
     * retorna. Este método es seguro para acceso concurrente.
     *
     * @return Instancia Singleton de HandlerActions.
     */
    public static synchronized HandlerActions getInstance() {
        if (instance == null) {
            instance = new HandlerActions();
        }
        return instance;
    }

    /**
     * Procesa una acción enviada por un cliente. Extrae la acción y el ID del
     * cliente del mapa de datos recibido, obtiene el socket correspondiente y
     * publica el evento usando EventBus para que el sistema maneje la acción.
     *
     * @param request Mapa que contiene detalles de la acción y del cliente.
     * @throws IOException En caso de error en la comunicación de red.
     * @throws Exception Para cualquier otra excepción que pueda ocurrir.
     */
    public void handlerAction(Map<String, Object> request) throws IOException, Exception {
        String accion = (String) request.get("accion");
        String clientId = (String) request.get("clientId");

        Socket clientSocket = ClientManager.getClientSocket(clientId);
        clientId = ClientManager.getClientId(clientSocket);

        request.put("clientId", clientId);

        EventBus.getInstance().publish(accion, request);
    }

    /**
     * Registra los eventos disponibles que pueden ser disparados por los
     * clientes. Cada evento tiene un handler que procesa la información
     * recibida y ejecuta la lógica de negocio correspondiente.
     */
    public void registrarEventos() {
        EventBus bus = EventBus.getInstance();

        // Evento para crear una nueva partida en el sistema.
        bus.subscribe(AccionesJugador.CREAR_PARTIDA.toString(), request -> {
            System.out.println("[EVENTO] CREAR_PARTIDA recibido: " + request);
            String clientId = (String) request.get("clientId");
            Socket clientSocket = ClientManager.getClientSocket(clientId);
            Map<String, Object> response = partidaBO.crearPartida(request, clientId);
            MessageUtil.enviarMensaje(clientSocket, response);
        });

        // Evento para unirse a una partida ya existente.
        bus.subscribe(AccionesJugador.UNIRSE_PARTIDA.toString(), request -> {
            System.out.println("[EVENTO] UNIRSE_PARTIDA recibido: " + request);
            String clientId = (String) request.get("clientId");
            Socket clientSocket = ClientManager.getClientSocket(clientId);
            Map<String, Object> response = partidaBO.unirsePartida(request, clientId);
            MessageUtil.enviarMensaje(clientSocket, response);
        });

        // Evento para marcar que un jugador está listo para iniciar la partida.
        bus.subscribe(AccionesJugador.JUGADOR_LISTO.toString(), request -> {
            System.out.println("[EVENTO] JUGADOR_LISTO recibido: " + request);
            try {
                String clientId = (String) request.get("clientId");
                partidaBO.jugadorListo(request, clientId);
                // Posible lugar para notificar a otros jugadores sobre el estado.
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Evento que representa la acción de colocar una unidad en el tablero.
        bus.subscribe(AccionesJugador.ORDENAR.toString(), request -> {
            System.out.println("[EVENTO] ORDENAR recibido: " + request);
            String clientId = (String) request.get("clientId");
            partidaBO.colocarUnidadTablero(request, clientId);
        });

        // Evento que maneja la acción de atacar en la partida.
        bus.subscribe(AccionesJugador.ATACAR.toString(), request -> {
            System.out.println("[EVENTO] ATACAR recibido: " + request);
            String clientId = (String) request.get("clientId");
            ModeloJugador otherClient = ClientManager.getOtherPlayer(clientId);
            Map<String, Object> response = partidaBO.ubicarAtaque(request, clientId);
            String resultado = (String) response.get("resultado");

            if (ControlPartida.PARTIDA_FINALIZADA.name().equalsIgnoreCase(resultado)) {
                MessageUtil.enviarMensaje(ClientManager.getClientSocket(clientId), response);
                MessageUtil.enviarMensaje(ClientManager.getClientSocket(otherClient.getId()), response);
            } else {
                Map<String, Object> atacanteResponse = (Map<String, Object>) response.get(clientId);
                Map<String, Object> atacadoResponse = (Map<String, Object>) response.get(otherClient.getId());

                MessageUtil.enviarMensaje(ClientManager.getClientSocket(clientId), atacanteResponse);
                MessageUtil.enviarMensaje(ClientManager.getClientSocket(otherClient.getId()), atacadoResponse);
            }
        });

        // Evento para que un jugador se rinda y termine la partida.
        bus.subscribe(AccionesJugador.RENDIRSE.toString(), request -> {
            System.out.println("[EVENTO] RENDIRSE recibido: " + request);
            String clientId = (String) request.get("clientId");
            partidaBO.rendirse(request, clientId);
        });

        // Evento para solicitar estadísticas del jugador.
        bus.subscribe(AccionesJugador.ESTADISTICAS.toString(), request -> {
            System.out.println("[EVENTO] ESTADISTICAS recibido: " + request);
            String clientId = (String) request.get("clientId");
            Map<String, Object> response = partidaBO.obtenerEstadisticasJugador(clientId);
            MessageUtil.enviarMensaje(ClientManager.getClientSocket(clientId), response);
        });

        // Evento para solicitar reiniciar la partida y volver a jugar.
        bus.subscribe(AccionesJugador.VOLVER_A_JUGAR.toString(), request -> {
            System.out.println("[EVENTO] VOLVER_A_JUGAR recibido: " + request);
            String clientId = (String) request.get("clientId");
            partidaBO.volverAJugar(clientId);
        });

        // Evento para recibir la respuesta de un jugador sobre volver a jugar.
        bus.subscribe(AccionesJugador.RESPUESTA_VOLVER_A_JUGAR.toString(), request -> {
            System.out.println("[EVENTO] RESPUESTA_VOLVER_A_JUGAR recibido: " + request);
            String clientId = (String) request.get("clientId");
            boolean acepta = (Boolean) request.get("acepta");
            partidaBO.respuestaVolverAJugar(clientId, acepta);
        });

        // Evento que indica que un jugador quiere salir de la partida.
        bus.subscribe(AccionesJugador.SALIR.toString(), request -> {
            System.out.println("[EVENTO] SALIR recibido: " + request);
            String clientId = (String) request.get("clientId");
            Map<String, Object> response = partidaBO.salir(clientId);
            MessageUtil.enviarMensaje(ClientManager.getClientSocket(clientId), response);
        });

    }

}
