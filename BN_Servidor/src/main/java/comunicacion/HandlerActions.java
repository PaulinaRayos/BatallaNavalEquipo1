/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import bo.PartidaBO;
import enums.AccionesJugador;
import eventos.EventBus;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

/**
 *
 * @author pauli
 */
public class HandlerActions {

    /**
     * Objeto que maneja la lógica de negocio para gestionar las partidas del
     * juego.
     */
    private PartidaBO partidaBO;

    /**
     * Instancia única de HandlerActions (patrón Singleton).
     */
    private static HandlerActions instance = null;

    /**
     * Constructor privado para asegurar que no se creen instancias adicionales.
     * Inicializa el objeto PartidaBO.
     */
    private HandlerActions() {
        this.partidaBO = new PartidaBO();
    }

    /**
     * Método estático para obtener la única instancia de HandlerActions. Si la
     * instancia no existe, se crea una nueva.
     *
     * @return La instancia única de HandlerActions.
     */
    public static synchronized HandlerActions getInstance() {
        if (instance == null) {
            instance = new HandlerActions();
        }
        return instance;
    }

    /**
     * Maneja las acciones enviadas por los clientes y las procesa según la
     * acción especificada. Esta función obtiene el clientId desde la solicitud,
     * obtiene el socket del cliente correspondiente, y luego publica el evento
     * con la acción y los datos proporcionados.
     *
     * @param request Mapa que contiene la información de la solicitud,
     * incluyendo la acción y el clientId.
     * @throws IOException Si ocurre un error de entrada/salida durante el
     * procesamiento.
     * @throws Exception Si ocurre un error general durante la ejecución del
     * proceso.
     */
    public void handlerAction(Map<String, Object> request) throws IOException, Exception {
        String accion = (String) request.get("accion");
        String clientId = (String) request.get("clientId"); // Puedes pasarlo desde la request

        Socket clientSocket = ClientManager.getClientSocket(clientId);
        clientId = ClientManager.getClientId(clientSocket); // Y obtener el clientId del socket

        request.put("clientId", clientId);

        EventBus.getInstance().publish(accion, request);
    }

    /**
     * Registra los eventos que pueden ser suscritos y procesados a través del
     * EventBus. Cada evento corresponde a una acción de un jugador, y se asocia
     * con un handler específico para procesar la solicitud y generar una
     * respuesta.
     */
    public void registrarEventos() {
        EventBus bus = EventBus.getInstance(); // Se obtiene la instancia del EventBus que manejará las suscripciones y los eventos.
        // Suscripción al evento CREAR_PARTIDA: cuando un cliente desea crear una partida.
        bus.subscribe(AccionesJugador.CREAR_PARTIDA.toString(), request -> {
            System.out.println("[EVENTO] CREAR_PARTIDA con datos: " + request);
            String clientId = (String) request.get("clientId");
            Socket clientSocket = ClientManager.getClientSocket(clientId);
            Map<String, Object> response = partidaBO.crearPartida(request, clientId);// llama al método del objeto partidaBO para crear una partida con los datos proporcionados
            MessageUtil.enviarMensaje(clientSocket, response);
        });
        // Suscripción al evento UNIRSE_PARTIDA: cuando un cliente desea unirse a una partida existente.
        bus.subscribe(AccionesJugador.UNIRSE_PARTIDA.toString(), request -> {
            System.out.println("[EVENTO] UNIRSE_PARTIDA con datos: " + request);
            String clientId = (String) request.get("clientId");
            Socket clientSocket = ClientManager.getClientSocket(clientId);
            Map<String, Object> response = partidaBO.unirsePartida(request, clientId);
            MessageUtil.enviarMensaje(clientSocket, response);
        });
        // Suscripción al evento JUGADOR_LISTO: cuando un jugador está listo para comenzar la partida.
        bus.subscribe(AccionesJugador.JUGADOR_LISTO.toString(), request -> {
            System.out.println("[EVENTO] JUGADOR_LISTO con datos: " + request);
            try {
                String clientId = (String) request.get("clientId");
                partidaBO.jugadorListo(request, clientId);
                // Poner una lógica que notifique a todos los jugadores?
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

}
