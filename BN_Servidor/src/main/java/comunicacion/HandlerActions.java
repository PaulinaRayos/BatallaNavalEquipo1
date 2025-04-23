/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import bo.PartidaBO;
import enums.AccionesJugador;
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
     * Método para manejar las acciones solicitadas por el cliente. Este método
     * procesa la acción solicitada, llama a los métodos correspondientes de
     * PartidaBO y envía las respuestas a los clientes.
     *
     * @param request Mapa que contiene los datos de la solicitud, incluyendo la
     * acción y otros parámetros necesarios.
     * @throws IOException En caso de error al enviar respuestas a los clientes.
     */
    public void handlerAction(Map<String, Object> request) throws IOException {
        String accion = (String) request.get("accion");
        String clientId = (String) request.get("clientId"); // Puedes pasarlo desde la request

        Socket clientSocket = ClientManager.getClientSocket(clientId);
        clientId = ClientManager.getClientId(clientSocket); // Y obtener el clientId del socket

        if (AccionesJugador.CREAR_PARTIDA.toString().equalsIgnoreCase(accion)) {
            Map<String, Object> response = partidaBO.crearPartida(request, clientId);
            MessageUtil.enviarMensaje(clientSocket, response);

        } else if (AccionesJugador.UNIRSE_PARTIDA.toString().equalsIgnoreCase(accion)) {
            Map<String, Object> response = partidaBO.unirsePartida(request, clientId);
            MessageUtil.enviarMensaje(clientSocket, response);

        } else if (AccionesJugador.JUGADOR_LISTO.toString().equalsIgnoreCase(accion)) {
            Map<String, Object> response = partidaBO.jugadorListo(request, clientId);
            // No es necesario responder al jugador, ya que notificaremos a todos
        } else if (AccionesJugador.ORDENAR.toString().equalsIgnoreCase(accion)) {
            //partidaBO.colocarUnidadTablero(request, clientId);**********************************************************
            // No es necesario responder al jugador, ya que notificaremos a todos
/**                                                                                           ******************************
        } else if (AccionesJugador.ATACAR.toString().equalsIgnoreCase(accion)) {
            System.out.println("Se recibio ataque");
            Jugador otherClient = ClientManager.getOtherPlayer(clientId);
            Map<String, Object> response = partidaBO.ubicarAtaque(request, clientId);
            String resultado = (String) response.get("resultado");
            //Aqui saco el resultado para ver si es de partida finalizada sino se envia el mensaje normal
            if (resultado != null && resultado.equalsIgnoreCase(ControlPartida.PARTIDA_FINALIZADA.name())) {
                MessageUtil.enviarMensaje(clientSocket, response);
                MessageUtil.enviarMensaje(ClientManager.getClientSocket(otherClient.getId()), response);
            }
            Map<String, Object> clienteAtacanteResponse = (Map<String, Object>) response.get(clientId);
            Map<String, Object> clienteAtacadoResponse = (Map<String, Object>) response.get(otherClient.getId());

            MessageUtil.enviarMensaje(clientSocket, clienteAtacanteResponse);
            MessageUtil.enviarMensaje(ClientManager.getClientSocket(otherClient.getId()), clienteAtacadoResponse);
        } else if (AccionesJugador.RENDIRSE.toString().equalsIgnoreCase(accion)) {
            partidaBO.rendirse(request, clientId);
        } else if (AccionesJugador.ESTADISTICAS.toString().equalsIgnoreCase(accion)) {
            Map<String, Object> response = partidaBO.obtenerEstadisticasJugador(clientId);
            MessageUtil.enviarMensaje(clientSocket, response);
        } else if (AccionesJugador.VOLVER_A_JUGAR.toString().equalsIgnoreCase(accion)) {
            partidaBO.volverAJugar(clientId);
        } else if (AccionesJugador.RESPUESTA_VOLVER_A_JUGAR.toString().equalsIgnoreCase(accion)) {
            boolean acepta = (Boolean) request.get("acepta");
            partidaBO.respuestaVolverAJugar(clientId, acepta);
        } else if (AccionesJugador.SALIR.toString().equalsIgnoreCase(accion)) {
            Map<String, Object> response = partidaBO.salir(clientId);
            // Enviar la respuesta al cliente
            MessageUtil.enviarMensaje(clientSocket, response);
        } else {
            System.out.println("Acción no reconocida: " + accion);*///*************************************************************************
        }
    }
}

