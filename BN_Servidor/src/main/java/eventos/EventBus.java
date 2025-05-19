/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos;

import java.util.HashMap;
import java.util.Map;

/**
 * La clase EventBus es un patrón de diseño utilizado para manejar eventos en un
 * sistema de manera centralizada. Permite a los componentes suscribirse a
 * eventos y ser notificados cuando dichos eventos se publican. Este EventBus
 * implementa un patrón singleton, lo que asegura que solo exista una instancia
 * global de la clase.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class EventBus {

    private static EventBus instance;
    private final Map<String, EventHandler> listeners = new HashMap<>();// Mapa que almacena los eventos y sus correspondientes manejadores (listeners)

    /**
     * Constructor privado para evitar instanciaciones fuera de la clase 
     */
    private EventBus() {
    }

    /**
     * Obtiene la instancia única de EventBus. Si aún no existe, la crea.
     *
     * @return La instancia única de EventBus.
     */
    public static synchronized EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    /**
     * Permite que un manejador (listener) se suscriba a un evento. Cuando el
     * evento es publicado, el manejador suscrito se ejecutará con los datos del
     * evento.
     *
     * @param evento Nombre del evento al que se desea suscribir.
     * @param manejador El objeto que maneja la lógica del evento cuando es
     * disparado.
     */
    public void subscribe(String evento, EventHandler manejador) {
        // Se agrega el manejador al mapa de listeners para el evento dado
        listeners.put(evento, manejador);
    }

    /**
     * Publica un evento y notifica a todos los suscriptores asociados con ese
     * evento. Si no existen suscriptores para el evento, muestra un mensaje de
     * advertencia.
     *
     * @param evento Nombre del evento que se está publicando.
     * @param datos Datos relacionados con el evento que serán enviados al
     * manejador.
     * @throws Exception Si ocurre un error durante la ejecución del manejador
     * del evento.
     */
    public void publish(String evento, Map<String, Object> datos) throws Exception {
        // Verifica si hay algún manejador suscrito al evento
        if (listeners.containsKey(evento)) {
            // Si existe un manejador, lo ejecuta
            listeners.get(evento).handle(datos);
        } else {
            // Si no hay suscriptores, muestra un mensaje indicando que no hay manejadores para ese evento
            System.out.println("No hay suscriptores para el evento: " + evento);
        }
    }
}
