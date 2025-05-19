/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import Modelo.ModeloJugador;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.core.MessagePack;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

/**
 * Clase encargada de manejar la comunicación con un cliente conectado al
 * servidor. Cada instancia representa un cliente conectado. Se encarga de
 * recibir los mensajes del cliente, desempaquetarlos, deserializarlos y
 * enviarlos al manejador central de acciones.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ClientHandler implements Runnable {

    /**
     * Socket asociado al cliente.
     */
    private final Socket clientSocket;

    /**
     * Manejador de acciones centralizado (Singleton).
     */
    private final HandlerActions handler;

    /**
     * Identificador único del cliente.
     */
    private final int id;

    /**
     * Constructor que inicializa el socket y el identificador del cliente.
     *
     * @param clientSocket Socket por el que el cliente se conecta al servidor.
     * @param id Identificador numérico del cliente (asignado por el servidor).
     */
    public ClientHandler(Socket clientSocket, int id) {
        this.clientSocket = clientSocket;
        this.handler = HandlerActions.getInstance(); // patrón Singleton
        this.id = id;
    }

    /**
     * Método que se ejecuta cuando inicia el hilo correspondiente a este
     * cliente. Escucha continuamente los mensajes del cliente, los desempaqueta
     * (MessagePack), convierte de JSON a Map, y los envía a `handlerAction`
     * para ser procesados.
     */
    @Override
    public void run() {
        try (
                InputStream inputStream = clientSocket.getInputStream(); MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(inputStream)) {
            // Se convierte el ID del cliente a String para usarlo como clientId
            String clientId = String.valueOf(this.id);

            // Se registra el nuevo cliente en el ClientManager junto con su ModeloJugador
            ClientManager.addClient(clientSocket, clientId, new ModeloJugador());

            // Bucle de escucha: mientras el socket esté abierto, procesa mensajes
            while (!clientSocket.isClosed()) {
                if (unpacker.hasNext()) {
                    // Desempaquetar string (en formato JSON) desde el flujo del cliente
                    String json = unpacker.unpackString();

                    // Convertir el JSON recibido en un Map de clave-valor
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> data = objectMapper.readValue(json, Map.class);

                    // Se añade el clientId al mensaje para identificar al remitente
                    data.put("clientId", clientId);

                    // Se delega el procesamiento del mensaje al handler central
                    this.handler.handlerAction(data);
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // Log de errores si ocurre algún problema de E/S o parsing
        } finally {
            try {
                clientSocket.close(); // Cierra el socket al finalizar
                ClientManager.removeClient(clientSocket); // Elimina cliente del registro
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
