/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estados;

import comunicacion.ComandoIniciarPartida;
import comunicacion.ComandoOponenteNoQuiereVolverAJugar;
import comunicacion.ComandoOponenteQuiereVolverAJugar;
import comunicacion.ConexionCliente;
import comunicacion.IComando;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import vista.VistaEstadisticas;
import vistaModelo.Juego;
import vistaModelo.VistaModeloEstadisticas;

/**
 * Representa el estado del juego correspondiente a la visualización de
 * estadísticas.
 *
 * En este estado se muestran los resultados de la partida, incluyendo el
 * ganador, el tiempo de duración y las estadísticas individuales. También
 * gestiona la lógica de volver a jugar o finalizar la sesión.
 *
 * @autor ivanochoa
 * @autor paulvazquez
 * @autor paulinarodriguez
 * @autor cuauhtemocvazquez
 */
public class EstadoEstadisticas implements IEstado {

    /**
     * Referencia al juego principal.
     */
    private Juego juego;

    /**
     * Vista que representa la interfaz de estadísticas.
     */
    private VistaEstadisticas vista;

    /**
     * VistaModelo asociado a la vista de estadísticas.
     */
    private VistaModeloEstadisticas vistaModelo;

    /**
     * Mapa que contiene los comandos disponibles en este estado.
     */
    private Map<String, IComando> comandos;

    /**
     * Mapa que contiene las estadísticas de los jugadores.
     */
    private Map<String, Object> estadisticas;

    /**
     * Constructor que inicializa el estado de estadísticas.
     *
     * @param juego la referencia al juego principal
     * @param estadisticas el mapa con las estadísticas de los jugadores
     * @param ganador el nombre del jugador ganador
     * @param tiempoPartida la duración de la partida
     */
    public EstadoEstadisticas(Juego juego, Map<String, Object> estadisticas, String ganador, String tiempoPartida) {
        this.juego = juego;
        this.estadisticas = estadisticas;
        this.vista = new VistaEstadisticas(juego.getPanel(), estadisticas, ganador, tiempoPartida, juego);
        inicializarComandos();
    }

    /**
     * Inicializa los comandos que pueden ser ejecutados en este estado.
     */
    private void inicializarComandos() {
        comandos = new HashMap<>();
        comandos.put("OPONENTE_QUIERE_VOLVER_A_JUGAR", new ComandoOponenteQuiereVolverAJugar(this));
        comandos.put("OPONENTE_NO_QUIERE_VOLVER_A_JUGAR", new ComandoOponenteNoQuiereVolverAJugar(this));
        comandos.put("INICIAR_PARTIDA", new ComandoIniciarPartida(this));
    }

    /**
     * Sale del estado de estadísticas y quita los componentes de la vista.
     */
    @Override
    public void salir() {
        vista.quitarComponentes();
    }

    /**
     * Renderiza la vista de estadísticas.
     *
     * @param g el objeto Graphics utilizado para dibujar la vista
     */
    @Override
    public void renderizar(Graphics g) {
        vista.dibujar(g);
    }

    /**
     * Maneja un mensaje recibido en el estado de estadísticas.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    @Override
    public void handleMessage(Map<String, Object> mensaje) {
        String accion = (String) mensaje.get("accion");
        if (accion != null && comandos.containsKey(accion)) {
            comandos.get(accion).execute(mensaje);
        } else {
            System.out.println("Acción no reconocida en EstadoEstadisticas: " + accion);
        }
    }

    /**
     * Maneja la lógica cuando se decide volver a jugar.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    public void manejarVolverAJugar(Map<String, Object> mensaje) {
        boolean oponenteQuiereJugar = (Boolean) mensaje.get("oponenteQuiereJugar");
        if (oponenteQuiereJugar) {
            JOptionPane.showMessageDialog(null, "El oponente quiere volver a jugar.");
            juego.cambiarEstado(new EstadoOrganizar(juego));
        } else {
            JOptionPane.showMessageDialog(null, "El oponente no quiere volver a jugar. Regresando al menú.");
            juego.cambiarEstado(new EstadoMenu(juego));
        }
    }

    /**
     * Maneja el inicio de una nueva partida si ambos jugadores están de
     * acuerdo.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    public void manejarIniciarPartida(Map<String, Object> mensaje) {
        JOptionPane.showMessageDialog(null, "Ambos jugadores han aceptado volver a jugar. Iniciando nueva partida.");
        juego.cambiarEstado(new EstadoOrganizar(juego));
    }

    /**
     * Maneja el caso en que el oponente sale del juego.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    public void manejarOponenteSalio(Map<String, Object> mensaje) {
        JOptionPane.showMessageDialog(null, "El oponente ha salido del juego. Regresando al menú.");
        juego.cambiarEstado(new EstadoMenu(juego));
    }

    /**
     * Maneja la solicitud del oponente para volver a jugar.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    public void manejarOponenteQuiereVolverAJugar(Map<String, Object> mensaje) {
        String textoMensaje = (String) mensaje.get("mensaje");
        int respuesta = JOptionPane.showConfirmDialog(null, textoMensaje, "Volver a Jugar", JOptionPane.YES_NO_OPTION);
        boolean acepta = (respuesta == JOptionPane.YES_OPTION);

        // Enviar la respuesta al servidor
        Map<String, Object> respuestaMensaje = new HashMap<>();
        respuestaMensaje.put("accion", "RESPUESTA_VOLVER_A_JUGAR");
        respuestaMensaje.put("acepta", acepta);

        ConexionCliente.getInstance().sendMessage(respuestaMensaje);

        if (!acepta) {
            JOptionPane.showMessageDialog(null, "Has decidido no volver a jugar. Regresando al menú.");
            juego.cambiarEstado(new EstadoMenu(juego));
        } else {
            JOptionPane.showMessageDialog(null, "Has aceptado volver a jugar. Esperando confirmación del oponente.");
        }
    }

    /**
     * Maneja el caso en que el oponente no quiere volver a jugar.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    public void manejarOponenteNoQuiereVolverAJugar(Map<String, Object> mensaje) {
        String textoMensaje = (String) mensaje.get("mensaje");
        JOptionPane.showMessageDialog(null, textoMensaje, "Volver a Jugar", JOptionPane.INFORMATION_MESSAGE);
        juego.cambiarEstado(new EstadoMenu(juego));
    }

}
