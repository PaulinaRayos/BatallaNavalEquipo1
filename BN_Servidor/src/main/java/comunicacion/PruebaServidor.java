/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package comunicacion;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase principal que implementa un servidor de sockets para aceptar y
 * gestionar múltiples conexiones de clientes de forma concurrente.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class PruebaServidor {

    /**
     * Contador seguro para generar identificadores únicos incrementales para
     * cada cliente que se conecta al servidor.
     */
    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    /**
     * Punto de entrada del servidor. Escucha continuamente en el puerto 5000
     * para aceptar nuevas conexiones de clientes, asignarles un identificador
     * único y crear un hilo dedicado para atender cada conexión.
     *
     * También inicializa el sistema de manejo de eventos registrados en
     * HandlerActions.
     *
     * @param args Parámetros de línea de comando (no utilizados en esta
     * aplicación).
     */
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Servidor esperando conexiones en el puerto 5000...");

            // Registrar todos los eventos y sus manejadores para la lógica del juego
            HandlerActions.getInstance().registrarEventos();

            while (true) {
                // Esperar y aceptar una nueva conexión de cliente
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado");

                // Obtener un identificador único para el cliente
                int id = atomicInteger.getAndIncrement();
                System.out.println("id del cliente " + id);

                // Lanzar un hilo para gestionar la comunicación con este cliente
                new Thread(new ClientHandler(clientSocket, id)).start();
            }
        } catch (Exception e) {
            // Mostrar errores que ocurran en la ejecución del servidor
            e.printStackTrace();
        }
    }
}
