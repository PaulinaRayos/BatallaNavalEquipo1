/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistaModelo;

import comunicacion.ConexionCliente;
import estados.EstadoMenu;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author pauli
 */
public class VistaModeloEstadisticas {

    private Juego juego;

    public VistaModeloEstadisticas(Juego juego) {
        this.juego = juego;
    }

    public void volverAJugar() {
        // Enviar solicitud al servidor para volver a jugar
        Map<String, Object> mensaje = new HashMap<>();
        mensaje.put("accion", "VOLVER_A_JUGAR");

        ConexionCliente.getInstance().sendMessage(mensaje);

        // Mostrar mensaje de espera
        JOptionPane.showMessageDialog(null, "Has solicitado volver a jugar. Esperando respuesta del oponente.");
    }

    public void salirAlMenu() {
        // Enviar notificación al servidor de que el jugador quiere salir
        Map<String, Object> mensaje = new HashMap<>();
        mensaje.put("accion", "SALIR");

        ConexionCliente.getInstance().sendMessage(mensaje);

        // Transicionar al estado del menú
        juego.cambiarEstado(new EstadoMenu(juego));
    }

}
