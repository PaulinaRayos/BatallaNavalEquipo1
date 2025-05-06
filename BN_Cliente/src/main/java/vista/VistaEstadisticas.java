/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.Graphics;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import vistaModelo.Juego;
import vistaModelo.VistaModeloEstadisticas;

/**
 *
 * @author pauli
 */
public class VistaEstadisticas implements IVistaPanel {

    private VistaPanel panelJuego;
    private VistaModeloEstadisticas vistaModelo;
    private JTable tablaEstadisticas;
    private JButton btnVolverAJugar;
    private JButton btnSalir;
    private Map<String, Object> estadisticas;
    private String ganador;
    private String tiempoPartida;

    private JLabel lblGanador;
    private JLabel lblTiempoPartida;
    private JLabel lblEstadisticas;
    private JScrollPane scrollPane;

    public VistaEstadisticas(VistaPanel panelJuego, Map<String, Object> estadisticas, String ganador, String tiempoPartida, Juego juego) {
        this.panelJuego = panelJuego;
        this.estadisticas = estadisticas;
        this.ganador = ganador;
        this.tiempoPartida = tiempoPartida;
        this.vistaModelo = new VistaModeloEstadisticas(juego);
        crearComponentes();
        accionesComponentes();
    }

    @Override
    public void crearComponentes() {
        // Crear etiquetas para el ganador y el tiempo de partida
        lblGanador = new JLabel("Ganador: " + ganador, SwingConstants.CENTER);
        lblGanador.setFont(VistaUtilidades.FUENTE_SUBTITULO);
        lblGanador.setForeground(VistaUtilidades.COLOR_TEXTO_AZUL_OSCURO);

        lblTiempoPartida = new JLabel("Tiempo de la partida: " + tiempoPartida, SwingConstants.CENTER);
        lblTiempoPartida.setFont(VistaUtilidades.FUENTE_SUBTITULO);
        lblTiempoPartida.setForeground(VistaUtilidades.COLOR_TEXTO_AZUL_OSCURO);

        // Crear la tabla de estadísticas
        crearTablaEstadisticas();

        // Crear los botones
        btnVolverAJugar = VistaUtilidades.crearBoton("Volver a jugar");
        btnSalir = VistaUtilidades.crearBoton("Salir");

        // Configurar los componentes y agregarlos al panel
        configurarComponentes();
    }

    @Override
    public void accionesComponentes() {
        btnVolverAJugar.addActionListener(e -> vistaModelo.volverAJugar());
        btnSalir.addActionListener(e -> vistaModelo.salirAlMenu());
    }

    @Override
    public void dibujar(Graphics g) {
        // Título y fondo
        g.setColor(VistaUtilidades.COLOR_FONDO);
        g.fillRect(0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO);
        g.setColor(VistaUtilidades.COLOR_TEXTO_AZUL_OSCURO);
        g.setFont(VistaUtilidades.FUENTE_TITULO);
        VistaUtilidades.dibujarTextoCentrado(g, "Estadísticas de la Partida", 50, VistaUtilidades.FUENTE_TITULO);
    }

    @Override
    public void quitarComponentes() {
        panelJuego.quitarComponente(lblGanador);
        panelJuego.quitarComponente(lblTiempoPartida);
        panelJuego.quitarComponente(lblEstadisticas);
        panelJuego.quitarComponente(scrollPane);
        panelJuego.quitarComponente(btnVolverAJugar);
        panelJuego.quitarComponente(btnSalir);

    }

    private void crearTablaEstadisticas() {
        // Obtener los nombres de los jugadores y las estadísticas
        String[] nombresJugadores = obtenerNombresJugadores();
        String[] nombresEstadisticas = {
            "Naves Destruidas",
            "Naves Restantes",
            "Disparos Acertados",
            "Disparos Fallados",
            "Disparos Totales"
        };

        // Crear el modelo de la tabla
        String[] columnasTabla = new String[nombresJugadores.length + 1];
        columnasTabla[0] = ""; // Primera columna sin encabezado
        System.arraycopy(nombresJugadores, 0, columnasTabla, 1, nombresJugadores.length);

        DefaultTableModel modeloTabla = new DefaultTableModel(columnasTabla, 0);

        // Agregar los datos al modelo
        Object[][] datos = obtenerDatosEstadisticas(nombresEstadisticas);
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }

        // Crear la tabla
        tablaEstadisticas = new JTable(modeloTabla);
        
        // colocar las fuentes
        tablaEstadisticas.setFont(VistaUtilidades.FUENTE_CAMPO_TEXTO);
        tablaEstadisticas.setForeground(VistaUtilidades.COLOR_BOTON_FONDO);
        tablaEstadisticas.setBackground(VistaUtilidades.COLOR_BOTON_TEXTO);

        tablaEstadisticas.getTableHeader().setFont(VistaUtilidades.FUENTE_SUBTITULO);
        tablaEstadisticas.getTableHeader().setForeground(VistaUtilidades.COLOR_BOTON_TEXTO);
        tablaEstadisticas.getTableHeader().setBackground(VistaUtilidades.COLOR_BOTON_FONDO);

        // Configurar la tabla
        tablaEstadisticas.setEnabled(false);
    }

    private String[] obtenerNombresJugadores() {
        // Obtener los nombres de los jugadores de las estadísticas
        // Asumiendo que las claves son los IDs de los jugadores
        Set<String> idsJugadores = estadisticas.keySet();
        String[] nombres = new String[idsJugadores.size()];
        int index = 0;
        for (String id : idsJugadores) {
            Map<String, Object> statsJugador = (Map<String, Object>) estadisticas.get(id);
            nombres[index++] = (String) statsJugador.get("nombre");
        }
        return nombres;
    }

    private Object[][] obtenerDatosEstadisticas(String[] nombresEstadisticas) {
        // Obtener los datos de las estadísticas para cada jugador
        Set<String> idsJugadores = estadisticas.keySet();
        int numJugadores = idsJugadores.size();
        int numEstadisticas = nombresEstadisticas.length;

        Object[][] datos = new Object[numEstadisticas][numJugadores + 1]; // +1 para el nombre de la estadística

        int row = 0;
        for (String nombreEstadistica : nombresEstadisticas) {
            datos[row][0] = nombreEstadistica; // Primera columna: nombre de la estadística
            int col = 1;
            for (String id : idsJugadores) {
                Map<String, Object> statsJugador = (Map<String, Object>) estadisticas.get(id);
                Object valor = statsJugador.get(nombreEstadistica.toLowerCase().replace(" ", "_"));
                datos[row][col++] = valor;
            }
            row++;
        }

        return datos;
    }

    private void configurarComponentes() {
        // Posicionar las etiquetas
        lblGanador.setBounds(0, 100, Juego.GAME_ANCHO, 30);
        panelJuego.agregarComponente(lblGanador, 0, 100, Juego.GAME_ANCHO, 30);

        lblTiempoPartida.setBounds(0, 140, Juego.GAME_ANCHO, 30);
        panelJuego.agregarComponente(lblTiempoPartida, 0, 140, Juego.GAME_ANCHO, 30);

        // Posicionar la etiqueta "Estadísticas"
        lblEstadisticas = new JLabel("Estadísticas", SwingConstants.CENTER);
        lblEstadisticas.setFont(VistaUtilidades.FUENTE_SUBTITULO);
        lblEstadisticas.setForeground(VistaUtilidades.COLOR_TEXTO_AZUL_OSCURO);
        lblEstadisticas.setBounds(0, 180, Juego.GAME_ANCHO, 30);
        panelJuego.agregarComponente(lblEstadisticas, 0, 180, Juego.GAME_ANCHO, 30);

        // Configurar y agregar la tabla
        scrollPane = new JScrollPane(tablaEstadisticas);
        scrollPane.setBounds(150, 220, 600, 115);
        panelJuego.agregarComponente(scrollPane, 150, 220, 600, 115);

        // Configurar y agregar los botones
        panelJuego.agregarComponente(btnVolverAJugar, ((Juego.GAME_ANCHO / 2) - 230), 660, 200, 30);

        // Configurar y agregar los botones
        panelJuego.agregarComponente(btnSalir, ((Juego.GAME_ANCHO / 2) + 90), 660, 200, 30);

        panelJuego.repaint();

    }

}
