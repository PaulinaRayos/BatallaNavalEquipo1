/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * Utilidad para el envío de mensajes a clientes a través de sockets.
 * Proporciona métodos estáticos para convertir datos a formato JSON y
 * empaquetarlos usando MessagePack antes de enviarlos.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class MessageUtil {

    /**
     * Envía un mensaje al cliente especificado mediante un socket. El mensaje
     * se recibe como un mapa de datos, se convierte a JSON, se empaqueta usando
     * MessagePack y finalmente se transmite por el socket.
     *
     * @param clientSocket Socket correspondiente al cliente receptor.
     * @param mensajeMap Mapa con la información que será enviada en el mensaje.
     */
    public static void enviarMensaje(Socket clientSocket, Map<String, Object> mensajeMap) {
        try {
            // Serializar el mapa a formato JSON utilizando Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMensaje = objectMapper.writeValueAsString(mensajeMap);

            // Empaquetar la cadena JSON con MessagePack para optimizar la transmisión
            MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
            packer.packString(jsonMensaje);
            packer.close();

            // Obtener el stream de salida del socket y enviar el paquete
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(packer.toByteArray());
            outputStream.flush();

        } catch (Exception e) {
            // Imprimir la traza de la excepción en caso de error
            e.printStackTrace();
        }
    }
}
