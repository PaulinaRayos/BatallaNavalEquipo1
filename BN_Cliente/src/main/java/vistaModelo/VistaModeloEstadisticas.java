/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistaModelo;

import comunicacion.ConexionCliente;
import estados.EstadoMenu;
import estados.EstadoOrganizar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 * ViewModel para la vista de estadísticas. Gestiona las acciones del usuario
 * relacionadas con la finalización de la partida, como volver a jugar o salir
 * al menú principal.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class VistaModeloEstadisticas {

    /**
     * Referencia a la clase principal del juego para cambiar estados.
     */
    private Juego juego;

    /**
     * Constructor que inicializa el ViewModel con la instancia principal del
     * juego.
     *
     * @param juego la instancia principal del juego
     */
    public VistaModeloEstadisticas(Juego juego) {
        this.juego = juego;
    }

    /**
     * Envía una solicitud al servidor para volver a jugar y muestra un mensaje
     * informando que se espera la respuesta del oponente.
     */
    public void volverAJugar() {
        Map<String, Object> mensaje = new HashMap<>();
        mensaje.put("accion", "VOLVER_A_JUGAR");

        ConexionCliente.getInstance().sendMessage(mensaje);

        JOptionPane.showMessageDialog(null, "Has solicitado volver a jugar. Esperando respuesta del oponente.");
        
        juego.cambiarEstado(new EstadoOrganizar(juego));
        
        
    }

    /**
     * Envía una notificación al servidor indicando que el jugador quiere salir,
     * y cambia el estado del juego a menú principal.
     */
    public void salirAlMenu() {
        Map<String, Object> mensaje = new HashMap<>();
        mensaje.put("accion", "SALIR");

        ConexionCliente.getInstance().sendMessage(mensaje);

        juego.cambiarEstado(new EstadoMenu(juego));
    }

}
