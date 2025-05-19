/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.SwingUtilities;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/**
 * Clase que gestiona la conexión del cliente con el servidor.
 * Permite enviar y recibir mensajes utilizando JSON empaquetado con MessagePack.
 * Implementa un hilo para la escucha asíncrona de mensajes entrantes.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ConexionCliente {

    /**
     * Instancia única de la clase.
     */
    private static ConexionCliente instance = null;

    /**
     * Socket para la conexión con el servidor.
     */
    private Socket socket;

    /**
     * Flujo de salida para enviar datos al servidor.
     */
    private OutputStream outputStream;

    /**
     * Flujo de entrada para recibir datos del servidor.
     */
    private InputStream inputStream;

    /**
     * Hilo que se encarga de escuchar mensajes entrantes.
     */
    private Thread listeningThread;

    /**
     * Controla si la conexión está activa y el hilo de escucha debe seguir
     * corriendo.
     */
    private boolean running = false;

    /**
     * ObjectMapper para serializar y deserializar objetos JSON.
     */
    private ObjectMapper objectMapper;

    /**
     * Listener que recibe los mensajes entrantes desde el servidor.
     */
    private MessageListener messageListener;

    /**
     * Constructor privado para implementar el patrón singleton. Inicializa el
     * ObjectMapper para manejo de JSON.
     */
    private ConexionCliente() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Método para obtener la instancia única de ConexionCliente. Si no existe,
     * crea una nueva.
     *
     * @return instancia singleton de ConexionCliente
     */
    public static synchronized ConexionCliente getInstance() {
        if (instance == null) {
            instance = new ConexionCliente();
        }
        return instance;
    }

    /**
     * Establece la conexión con el servidor en el host y puerto indicados.
     * Inicializa los flujos de entrada y salida, y comienza el hilo de escucha.
     *
     * @param host dirección del servidor
     * @param port puerto del servidor
     * @return true si la conexión fue exitosa; false si hubo error
     */
    public boolean connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
            startListening();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cierra la conexión con el servidor y detiene el hilo de escucha. Libera
     * recursos asociados al socket y flujos.
     */
    public void disconnect() {
        running = false;
        if (listeningThread != null) {
            listeningThread.interrupt();
        }
        try {
            if (socket != null) {
                socket.close();
            }
            socket = null;
            outputStream = null;
            inputStream = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicia un hilo que escucha mensajes entrantes del servidor de forma
     * asíncrona. Los mensajes recibidos son deserializados y enviados al
     * listener registrado, ejecutándose en el hilo de la interfaz gráfica
     * (Event Dispatch Thread).
     */
    private void startListening() {
        running = true;
        listeningThread = new Thread(() -> {
            try {
                MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(inputStream);
                while (running) {
                    if (unpacker.hasNext()) {
                        // Desempaqueta el mensaje JSON recibido en formato String
                        String jsonMessage = unpacker.unpackString();
                        // Deserializa el JSON a un Map para facilitar su manejo
                        Map<String, Object> message = objectMapper.readValue(jsonMessage, Map.class);
                        // Si existe un listener registrado, se le notifica en el hilo de GUI
                        if (messageListener != null) {
                            SwingUtilities.invokeLater(() -> {
                                messageListener.onMessageReceived(message);
                            });
                        }
                    } else {
                        // Pausa breve para evitar consumo excesivo de CPU
                        Thread.sleep(100);
                    }
                }
                unpacker.close();
            } catch (IOException e) {
                // Imprime la excepción sólo si la conexión sigue activa
                if (running) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                // Interrupción del hilo, salida limpia
            }
        });
        listeningThread.start();
    }

    /**
     * Envía un mensaje al servidor. El mensaje es un Map que se serializa a
     * JSON y luego se empaqueta con MessagePack antes de enviarlo por el flujo
     * de salida.
     *
     * @param data Map con los datos del mensaje a enviar
     */
    public synchronized void sendMessage(Map<String, Object> data) {
        if (socket == null || outputStream == null) {
            System.out.println("Not connected to server.");
            return;
        }
        try {
            // Convierte el Map a cadena JSON
            String json = objectMapper.writeValueAsString(data);
            // Empaqueta el JSON en formato MessagePack para enviar
            MessagePacker packer = MessagePack.newDefaultPacker(outputStream);
            packer.packString(json);
            packer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Solicita al servidor la creación de una nueva partida, enviando el nombre
     * del jugador que crea la partida.
     *
     * @param nombreJugador nombre del jugador que crea la partida
     */
    public void crearPartida(String nombreJugador) {
        Map<String, Object> data = new HashMap<>();
        data.put("accion", "CREAR_PARTIDA");
        data.put("nombre", nombreJugador);
        sendMessage(data);
    }

    /**
     * Solicita unirse a una partida existente en el servidor, enviando el
     * código de acceso y el nombre del jugador.
     *
     * @param codigoAcceso código de acceso a la partida
     * @param nombreJugador nombre del jugador que se une a la partida
     */
    public void unirsePartida(String codigoAcceso, String nombreJugador) {
        Map<String, Object> data = new HashMap<>();
        data.put("accion", "UNIRSE_PARTIDA");
        data.put("codigo_acceso", codigoAcceso);
        data.put("nombre", nombreJugador);
        sendMessage(data);
    }

    /**
     * Envía un comando de ataque con las coordenadas especificadas al servidor.
     *
     * @param x coordenada X del ataque
     * @param y coordenada Y del ataque
     */
    public void atacar(int x, int y) {
        Map<String, Object> data = new HashMap<>();
        data.put("accion", "ATACAR");
        data.put("x", x);
        data.put("y", y);
        sendMessage(data);
    }

    /**
     * Registra el oyente que recibirá los mensajes entrantes del servidor.
     *
     * @param listener instancia que implementa la interfaz MessageListener
     */
    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }

    /**
     * Notifica al servidor que el jugador está listo para continuar.
     */
    public void jugadorListo() {
        Map<String, Object> data = new HashMap<>();
        data.put("accion", "JUGADOR_LISTO");
        sendMessage(data);
    }

    /**
     * Envía al servidor la información sobre la ubicación de las unidades en el
     * juego.
     *
     * @param unidadesData lista de mapas con los datos de cada unidad
     */
    public void enviarUnidades(List<Map<String, Object>> unidadesData) {
        Map<String, Object> data = new HashMap<>();
        data.put("accion", "ORDENAR");
        data.put("unidades", unidadesData);
        sendMessage(data);
    }

    /**
     * Envía al servidor los datos relativos a la rendición del jugador.
     *
     * @param datos mapa con la información de la rendición
     */
    public void enviarRendicion(Map<String, Object> datos) {
        sendMessage(datos);
    }

    /**
     * Interfaz para definir un oyente que maneje los mensajes entrantes
     * provenientes del servidor.
     */
    public interface MessageListener {

        /**
         * Método llamado cuando se recibe un mensaje desde el servidor.
         *
         * @param message mensaje recibido, deserializado como Map
         */
        void onMessageReceived(Map<String, Object> message);
    }
}
