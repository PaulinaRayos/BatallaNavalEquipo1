/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import Modelo.ModeloJugador;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Clase responsable de gestionar las conexiones de los clientes, asociando
 * sockets con sus identificadores y jugadores correspondientes.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ClientManager {

    /**
     * Mapa que relaciona un socket con su identificador de cliente.
     */
    private static Map<Socket, String> clientToIdMap = new ConcurrentHashMap<>();

    /**
     * Mapa que relaciona un identificador de cliente con su socket.
     */
    private static Map<String, Socket> idToClientMap = new ConcurrentHashMap<>();

    /**
     * Mapa que relaciona un identificador de cliente con su modelo de jugador.
     */
    private static Map<String, ModeloJugador> clientIdToJugadorMap = new ConcurrentHashMap<>();

    /**
     * Agrega un nuevo cliente al sistema y establece sus relaciones.
     *
     * @param clientSocket Socket del cliente.
     * @param clientId Identificador del cliente.
     * @param jugador Objeto del modelo asociado al cliente.
     */
    public static void addClient(Socket clientSocket, String clientId, ModeloJugador jugador) {
        if (clientSocket == null || clientId == null || jugador == null) {
            System.out.println("Error: Los valores de clientSocket, clientId o jugador no pueden ser nulos.");
            return;
        }

        clientToIdMap.put(clientSocket, clientId);
        idToClientMap.put(clientId, clientSocket);
        clientIdToJugadorMap.put(clientId, jugador);

        System.out.println("Cliente agregado: " + clientId);
    }

    /**
     * Retorna el jugador asociado a un identificador de cliente.
     *
     * @param clientId Identificador del cliente.
     * @return ModeloJugador asociado, o null si no existe.
     */
    public static synchronized ModeloJugador getJugadorByClientId(String clientId) {
        if (clientId == null) {
            System.out.println("Error: El clientId no puede ser nulo.");
            return null;
        }
        return clientIdToJugadorMap.get(clientId);
    }

    /**
     * Retorna el identificador de cliente asociado a un socket.
     *
     * @param clientSocket Socket del cliente.
     * @return Identificador del cliente, o null si no existe.
     */
    public static synchronized String getClientId(Socket clientSocket) {
        if (clientSocket == null) {
            System.out.println("Error: El clientSocket no puede ser nulo.");
            return null;
        }
        return clientToIdMap.get(clientSocket);
    }

    /**
     * Retorna el socket asociado a un identificador de cliente.
     *
     * @param clientId Identificador del cliente.
     * @return Socket correspondiente, o null si no existe.
     */
    public static synchronized Socket getClientSocket(String clientId) {
        if (clientId == null) {
            System.out.println("Error: El clientId no puede ser nulo.");
            return null;
        }
        return idToClientMap.get(clientId);
    }

    /**
     * Elimina un cliente del sistema usando su socket.
     *
     * @param clientSocket Socket del cliente a eliminar.
     */
    public static synchronized void removeClient(Socket clientSocket) {
        if (clientSocket == null) {
            System.out.println("Error: El clientSocket no puede ser nulo al intentar eliminarlo.");
            return;
        }

        String clientId = clientToIdMap.remove(clientSocket);
        if (clientId != null) {
            idToClientMap.remove(clientId);
            clientIdToJugadorMap.remove(clientId);
            System.out.println("Cliente eliminado: " + clientId);
        } else {
            System.out.println("No se encontró el cliente asociado al socket.");
        }
    }

    /**
     * Retorna un jugador distinto al que tiene el identificador dado.
     *
     * @param excludeClientId Identificador del cliente a excluir.
     * @return Otro ModeloJugador disponible, o null si no hay otro.
     */
    public static synchronized ModeloJugador getOtherPlayer(String excludeClientId) {
        if (excludeClientId == null) {
            System.out.println("Error: El excludeClientId no puede ser nulo.");
            return null;
        }

        for (Map.Entry<String, ModeloJugador> entry : clientIdToJugadorMap.entrySet()) {
            if (!entry.getKey().equals(excludeClientId)) {
                return entry.getValue();
            }
        }

        System.out.println("No se encontró un jugador que no coincida con el clientId: " + excludeClientId);
        return null;
    }

    /**
     * Retorna el mapa completo de clientId a ModeloJugador.
     *
     * @return Mapa actual de jugadores registrados.
     */
    public static synchronized Map<String, ModeloJugador> getClientIdToJugadorMap() {
        return clientIdToJugadorMap;
    }
}
