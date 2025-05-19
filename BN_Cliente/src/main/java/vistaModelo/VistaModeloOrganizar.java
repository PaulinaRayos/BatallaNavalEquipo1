/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistaModelo;

import modelo.ModeloCasilla;
import modelo.ModeloTablero;
import comunicacion.ConexionCliente;
import interfaz.IVistaOrganizar;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import modelo.ModeloJugador;
import modelo.ModeloUbicacionUnidad;
import modelo.ModeloUnidad;
import vista.VistaTablero;
import vista.VistaUtilidades;

/**
 * ViewModel para la vista de organización de unidades. Gestiona la interacción
 * entre la vista y el modelo para organizar las unidades del jugador.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class VistaModeloOrganizar {

    /**
     * Vista de organización de unidades.
     */
    private IVistaOrganizar vista;

    /**
     * Modelo del jugador.
     */
    private ModeloJugador modeloJugador;

    /**
     * Conexión con el servidor.
     */
    private ConexionCliente conexionCliente;

    /**
     * Constructor que inicializa el ViewModel con la vista especificada, el
     * modelo del jugador y la conexión con el servidor.
     *
     * @param vista la vista de organización que será gestionada
     */
    public VistaModeloOrganizar(IVistaOrganizar vista) {
        this.vista = vista;
        this.modeloJugador = ModeloJugador.getInstance();
        this.conexionCliente = ConexionCliente.getInstance();
    }

    /**
     * Envía al servidor la información de las unidades organizadas por el
     * jugador. Se serializan las unidades con su número de nave y las
     * coordenadas ocupadas. Luego bloquea la interfaz para evitar más
     * modificaciones.
     */
    public void enviarUnidadesAlServidor() {
        VistaTablero tablero = vista.getTablero();
        ModeloTablero modeloTablero = tablero.getVistaModelo().getModeloTablero();

        // Obtener las unidades del tablero
        Set<ModeloUbicacionUnidad> unidades = modeloTablero.getUnidades();

        // Serializar las unidades a una lista de mapas con sus datos
        List<Map<String, Object>> unidadesData = new ArrayList<>();

        for (ModeloUbicacionUnidad ubicacionUnidad : unidades) {
            Map<String, Object> unidadData = new HashMap<>();
            ModeloUnidad unidad = ubicacionUnidad.getUnidad();

            unidadData.put("numNave", unidad.getNumNave());

            // Obtener las coordenadas ocupadas por la unidad
            List<Map<String, Integer>> coordenadas = new ArrayList<>();
            for (ModeloCasilla casilla : ubicacionUnidad.getCasillasOcupadas()) {
                Map<String, Integer> coordenada = new HashMap<>();
                coordenada.put("x", casilla.getCoordenada().getX());
                coordenada.put("y", casilla.getCoordenada().getY());
                coordenadas.add(coordenada);
            }
            unidadData.put("coordenadas", coordenadas);

            unidadesData.add(unidadData);
        }

        // Enviar la información de las unidades al servidor
        conexionCliente.enviarUnidades(unidadesData);

        // Bloquear la interfaz para evitar más cambios
        vista.bloquearInterfaz();
    }

    /**
     * Cambia el color de las naves en la vista de organización. Actualiza el
     * color en el tablero y en los paneles laterales.
     *
     * @param nombreColor nombre del nuevo color para las naves
     */
    public void cambiarColorNaves(String nombreColor) {
        Color nuevoColorNave = VistaUtilidades.obtenerColorBarco(nombreColor);
        vista.getTablero().setColorNave(nuevoColorNave);
        vista.actualizarColorPanelesLaterales(nuevoColorNave);
    }

    /**
     * Muestra un mensaje en la vista indicando que un jugador está esperando.
     *
     * @param nombreJugador nombre del jugador que está en espera
     */
    public void manejarJugadorEsperando(String nombreJugador) {
        vista.mostrarMensajeJugadorEsperando(nombreJugador);
    }

    /**
     * Cambia la vista a la pantalla de juego cuando se inicia la partida.
     */
    public void manejarIniciarJuego() {
        vista.navegarAJugar();
    }

}
