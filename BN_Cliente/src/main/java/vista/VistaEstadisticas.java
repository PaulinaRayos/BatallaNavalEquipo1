/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import vistaModelo.Juego;
import vistaModelo.VistaModeloEstadisticas;

/**
 *
 * @author pauli
 */
public class VistaEstadisticas implements IVistaPanel {

    // ==================== CONSTANTES DE ESTILO ====================
    private static final Color COLOR_FONDO = new Color(5, 20, 40);
    private static final Color COLOR_TEXTO = new Color(180, 220, 255);
    private static final Color COLOR_DESTACADO = new Color(255, 210, 0);
    private static final Color COLOR_BOTON_FONDO = new Color(0, 60, 120);
    private static final Color COLOR_BOTON_TEXTO = Color.WHITE;
    private static final Color COLOR_TABLA_FONDO = new Color(10, 30, 60, 200);
    private static final Color COLOR_TABLA_TEXTO = new Color(180, 200, 255);
    private static final Color COLOR_TABLA_GRID = new Color(100, 140, 200);

    private static final Font FUENTE_TITULO = new Font("Stencil", Font.BOLD, 32);
    private static final Font FUENTE_SUBTITULO = new Font("Arial Narrow", Font.BOLD, 20);
    private static final Font FUENTE_NORMAL = new Font("Arial", Font.PLAIN, 14);

    private static final String TEXTO_TITULO = "RESULTADOS DE LA BATALLA";
    private static final String TEXTO_GANADOR = "ALMIRANTE VICTORIOSO: ";
    private static final String TEXTO_TIEMPO = "DURACIÓN DE LA BATALLA: ";
    private static final String TEXTO_BOTON_REJUGAR = "NUEVA BATALLA";
    private static final String TEXTO_BOTON_SALIR = "RETIRAR FLOTA";

    // ==================== ATRIBUTOS ====================
    private VistaPanel panelJuego;
    private VistaModeloEstadisticas vistaModelo;
    private JTable tablaEstadisticas;
    private JButton btnVolverAJugar, btnSalir;
    private Map<String, Object> estadisticas;
    private String ganador, tiempoPartida;
    private JLabel lblGanador, lblTiempoPartida, lblTitulo;
    private JScrollPane scrollPane;

    public VistaEstadisticas(VistaPanel panelJuego, Map<String, Object> estadisticas,
            String ganador, String tiempoPartida, Juego juego) {
        this.panelJuego = panelJuego;
        this.estadisticas = estadisticas;
        this.ganador = ganador;
        this.tiempoPartida = tiempoPartida;
        this.vistaModelo = new VistaModeloEstadisticas(juego);
        crearComponentes();
        configurarComponentes();
        accionesComponentes();
    }

    @Override
    public void crearComponentes() {
        // Título principal
        lblTitulo = new JLabel("RESULTADOS DE LA BATALLA", SwingConstants.CENTER);
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_DESTACADO);

        // Etiqueta del ganador
        lblGanador = new JLabel(TEXTO_GANADOR + ganador, SwingConstants.CENTER);
        lblGanador.setFont(FUENTE_SUBTITULO);
        lblGanador.setForeground(COLOR_DESTACADO);

        // Etiqueta del tiempo
        lblTiempoPartida = new JLabel(TEXTO_TIEMPO + tiempoPartida, SwingConstants.CENTER);
        lblTiempoPartida.setFont(FUENTE_SUBTITULO);
        lblTiempoPartida.setForeground(COLOR_TEXTO);

        // Tabla de estadísticas
        crearTablaEstadisticas();

        // Botones con estilo naval
        btnVolverAJugar = new JButton(TEXTO_BOTON_REJUGAR);
        btnVolverAJugar.setFont(FUENTE_SUBTITULO);
        btnVolverAJugar.setBackground(COLOR_BOTON_FONDO);
        btnVolverAJugar.setForeground(COLOR_BOTON_TEXTO);
        btnVolverAJugar.setBorderPainted(false);
        btnVolverAJugar.setFocusPainted(false);

        btnSalir = new JButton(TEXTO_BOTON_SALIR);
        btnSalir.setFont(FUENTE_SUBTITULO);
        btnSalir.setBackground(COLOR_BOTON_FONDO);
        btnSalir.setForeground(COLOR_BOTON_TEXTO);
        btnSalir.setBorderPainted(false);
        btnSalir.setFocusPainted(false);
    }

    private void crearTablaEstadisticas() {
        String[] nombresJugadores = obtenerNombresJugadores();
        String[] nombresEstadisticas = {
            "Naves Destruidas",
            "Naves Restantes",
            "Disparos Acertados",
            "Disparos Fallados",
            "Disparos Totales"
        };

        String[] columnasTabla = new String[nombresJugadores.length + 1];
        columnasTabla[0] = "";
        System.arraycopy(nombresJugadores, 0, columnasTabla, 1, nombresJugadores.length);

        DefaultTableModel modeloTabla = new DefaultTableModel(columnasTabla, 0);
        Object[][] datos = obtenerDatosEstadisticas(nombresEstadisticas);

        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }

        tablaEstadisticas = new JTable(modeloTabla);
        tablaEstadisticas.setFont(FUENTE_NORMAL);
        tablaEstadisticas.setForeground(COLOR_TABLA_TEXTO);
        tablaEstadisticas.setBackground(COLOR_TABLA_FONDO);
        tablaEstadisticas.setGridColor(COLOR_TABLA_GRID);
        tablaEstadisticas.setEnabled(false);

        JTableHeader header = tablaEstadisticas.getTableHeader();
        header.setFont(FUENTE_SUBTITULO);
        header.setForeground(COLOR_BOTON_TEXTO);
        header.setBackground(COLOR_BOTON_FONDO);
    }

    private String[] obtenerNombresJugadores() {
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
        Set<String> idsJugadores = estadisticas.keySet();
        int numJugadores = idsJugadores.size();
        Object[][] datos = new Object[nombresEstadisticas.length][numJugadores + 1];

        for (int row = 0; row < nombresEstadisticas.length; row++) {
            datos[row][0] = nombresEstadisticas[row];
            int col = 1;
            for (String id : idsJugadores) {
                Map<String, Object> statsJugador = (Map<String, Object>) estadisticas.get(id);
                String key = nombresEstadisticas[row].toLowerCase().replace(" ", "_");
                datos[row][col++] = statsJugador.get(key);
            }
        }
        return datos;
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(COLOR_FONDO);
        g.fillRect(0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO);

    }

    private void configurarComponentes() {
        // Posicionamiento con parámetros correctos (x, y, width, height)
        panelJuego.agregarComponente(lblTitulo, 0, 70, Juego.GAME_ANCHO, 40);
        panelJuego.agregarComponente(lblGanador, 0, 120, Juego.GAME_ANCHO, 30);
        panelJuego.agregarComponente(lblTiempoPartida, 0, 160, Juego.GAME_ANCHO, 30);

        scrollPane = new JScrollPane(tablaEstadisticas);
        panelJuego.agregarComponente(scrollPane, 100, 200, 700, 150);

        panelJuego.agregarComponente(btnVolverAJugar, 150, 400, 200, 40);
        panelJuego.agregarComponente(btnSalir, 450, 400, 200, 40);

        panelJuego.repaint();
    }

    @Override
    public void accionesComponentes() {
        btnVolverAJugar.addActionListener(e -> vistaModelo.volverAJugar());
        btnSalir.addActionListener(e -> vistaModelo.salirAlMenu());
    }

    @Override
    public void quitarComponentes() {
        panelJuego.quitarComponente(lblTitulo);
        panelJuego.quitarComponente(lblGanador);
        panelJuego.quitarComponente(lblTiempoPartida);
        panelJuego.quitarComponente(scrollPane);
        panelJuego.quitarComponente(btnVolverAJugar);
        panelJuego.quitarComponente(btnSalir);
    }

}
