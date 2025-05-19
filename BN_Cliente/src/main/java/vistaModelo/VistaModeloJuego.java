/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistaModelo;

import comunicacion.ConexionCliente;
import enums.ControlPartida;
import estados.EstadoEstadisticas;
import interfaz.IVistaJuego;
import java.util.HashMap;
import java.util.Map;
import modelo.ModeloJugador;

/**
 * ViewModel para la vista del juego. Gestiona la lógica de la partida,
 * comunicación con el servidor, manejo de ataques, actualización de la interfaz
 * y transición entre estados.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class VistaModeloJuego implements AtaqueListener {

    /**
     * Vista del juego que maneja la interfaz gráfica.
     */
    private IVistaJuego vista;

    /**
     * Modelo que contiene los datos del jugador actual.
     */
    private ModeloJugador modeloJugador;

    /**
     * Conexión con el servidor para enviar y recibir mensajes.
     */
    private ConexionCliente conexionCliente;

    /**
     * Referencia a la clase principal del juego para cambiar estados.
     */
    private Juego juego;

    /**
     * Mapa que contiene las estadísticas de la partida para los jugadores.
     */
    private Map<String, Object> estadisticas;

    /**
     * Nombre del jugador ganador de la partida.
     */
    private String ganador;

    /**
     * Tiempo total de duración de la partida.
     */
    private String tiempoPartida;

    /**
     * Constructor que inicializa el ViewModel con la vista del juego y la
     * instancia del juego.
     *
     * @param vista la vista del juego
     * @param juego la instancia principal del juego
     */
    public VistaModeloJuego(IVistaJuego vista, Juego juego) {
        this.vista = vista;
        this.juego = juego;
        this.modeloJugador = ModeloJugador.getInstance();
        this.conexionCliente = ConexionCliente.getInstance();
    }

    /**
     * Inicializa la partida mostrando el nombre del oponente y el turno
     * inicial.
     *
     * @param nombreOponente el nombre del jugador contrario
     * @param esMiTurno true si es el turno del jugador actual, false en caso
     * contrario
     */
    public void inicializarJuego(String nombreOponente, boolean esMiTurno) {
        vista.setNombreOponente(nombreOponente);
        vista.actualizarInterfazTurno(esMiTurno);
    }

    /**
     * Maneja la respuesta del servidor a un ataque realizado o recibido,
     * actualizando el tablero, la vida de las naves y el turno de juego.
     *
     * @param mensaje mapa con los datos de la respuesta del servidor
     */
    public void manejarAtaqueResponse(Map<String, Object> mensaje) {
        String resultado = (String) mensaje.get("resultado");
        String mensajeTexto = (String) mensaje.get("mensaje");
        Integer vidaNave = (Integer) mensaje.get("vida_nave");
        Integer numeroNave = (Integer) mensaje.get("numero_nave");
        Integer x = (Integer) mensaje.get("x");
        Integer y = (Integer) mensaje.get("y");
        String turnoJugador = (String) mensaje.get(ControlPartida.DETERMINAR_TURNO.name());
        String ganador = (String) mensaje.get("ganador");

        boolean esMiTurno = turnoJugador != null && turnoJugador.equals(modeloJugador.getNombre());
        vista.actualizarInterfazTurno(esMiTurno);

        if (x != null && y != null) {
            if (resultado != null && resultado.startsWith("RESULTADO_ATAQUE_REALIZADO")) {
                boolean impacto = resultado.contains("IMPACTO");
                vista.actualizarTableroEnemigo(x, y, impacto);
                if (impacto && numeroNave != null && vidaNave != null) {
                    vista.actualizarEstadoFlotaEnemigo(numeroNave, vidaNave);
                }
            } else if (resultado != null && resultado.startsWith("RESULTADO_ATAQUE_RECIBIDO")) {
                boolean impacto = resultado.contains("IMPACTO");
                vista.actualizarTableroJugador(x, y, impacto);
                if (impacto && numeroNave != null && vidaNave != null) {
                    vista.actualizarEstadoFlotaJugador(numeroNave, vidaNave);
                }
            }
        }

        if ("PARTIDA_FINALIZADA".equals(resultado)) {
            vista.finalizarJuego(ganador);
        } else {
            vista.mostrarUltimoMensaje(mensajeTexto);
        }
    }

    /**
     * Envía un ataque al servidor en las coordenadas especificadas.
     *
     * @param x la coordenada x del ataque
     * @param y la coordenada y del ataque
     */
    public void atacar(int x, int y) {
        conexionCliente.atacar(x, y);
    }

    /**
     * Envía un ataque "vacío" al servidor para casos especiales.
     */
    public void enviarAtaqueVacio() {
        conexionCliente.atacar(10, 10);
    }

    /**
     * Método llamado cuando se realiza un ataque, detiene el temporizador de la
     * vista.
     */
    @Override
    public void enAtaqueRealizado() {
        vista.detenerTemporizador();
    }

    /**
     * Envía un mensaje de rendición al servidor indicando que el jugador se
     * rinde.
     */
    public void enviarRendicion() {
        Map<String, Object> datos = new HashMap<>();
        datos.put("accion", "RENDIRSE");
        datos.put("id_jugador", modeloJugador.getId());
        ConexionCliente.getInstance().enviarRendicion(datos);
    }

    /**
     * Finaliza la partida debido a la rendición y muestra el ganador en la
     * vista.
     *
     * @param ganador el nombre del jugador ganador tras la rendición
     */
    public void finalizarJuegoPorRendicion(String ganador) {
        vista.finalizarJuegoPorRendicion(ganador);
    }

    /**
     * Establece las estadísticas de la partida.
     *
     * @param estadisticas mapa con estadísticas relevantes
     */
    public void setEstadisticas(Map<String, Object> estadisticas) {
        this.estadisticas = estadisticas;
    }

    /**
     * Establece el nombre del ganador de la partida.
     *
     * @param ganador nombre del jugador ganador
     */
    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    /**
     * Establece el tiempo total de duración de la partida.
     *
     * @param tiempoPartida duración de la partida en formato texto
     */
    public void setTiempoPartida(String tiempoPartida) {
        this.tiempoPartida = tiempoPartida;
    }

    /**
     * Acción para continuar y navegar a la vista de estadísticas.
     */
    public void continuarEstadisticas() {
        juego.cambiarEstado(new EstadoEstadisticas(juego, estadisticas, ganador, tiempoPartida));
    }
}
