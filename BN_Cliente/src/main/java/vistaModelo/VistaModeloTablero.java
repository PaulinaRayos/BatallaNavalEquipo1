/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistaModelo;

import comunicacion.ConexionCliente;
import modelo.ModeloCasilla;
import modelo.ModeloTablero;
import enums.Orientacion;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.SwingUtilities;
import modelo.ModeloCasilla;
import modelo.ModeloTablero;
import modelo.ModeloTipoUnidad;
import modelo.ModeloUbicacionUnidad;
import modelo.ModeloUnidad;
import vista.VistaTablero;

/**
 * Clase VistaModeloTablero que actúa como intermediaria entre la vista y el
 * modelo para el tablero de juego, manejando interacciones de usuario y
 * actualizaciones del estado.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class VistaModeloTablero {

    /**
     * Modelo del tablero del juego.
     */
    private ModeloTablero modeloTablero;

    /**
     * Vista del tablero del juego.
     */
    private VistaTablero vista;

    /**
     * Indica si se está arrastrando una unidad en el tablero.
     */
    private boolean isDragging;

    /**
     * Casilla inicial donde se comenzó la interacción (arrastre o selección).
     */
    private ModeloCasilla casillaInicial;

    /**
     * Unidad seleccionada para ser movida o rotada.
     */
    private ModeloUbicacionUnidad unidadSeleccionada;

    /**
     * Conjunto de casillas originales que ocupaba la unidad antes de ser
     * movida.
     */
    private Set<ModeloCasilla> casillasOriginales;

    /**
     * Orientación original de la unidad antes de cualquier operación de
     * rotación.
     */
    private Orientacion orientacionOriginal;

    /**
     * Conjunto de casillas resaltadas durante la interacción para indicar las
     * posiciones posibles.
     */
    private Set<ModeloCasilla> celdasResaltadas = new HashSet<>();

    /**
     * Listener para los eventos de ataque.
     */
    private AtaqueListener ataqueListener;

    /**
     * Constructor que inicializa el presentador con la vista especificada.
     *
     * @param vista la vista del tablero
     */
    public VistaModeloTablero(VistaTablero vista) {
        this.vista = vista;
        this.modeloTablero = new ModeloTablero();

    }

    /**
     * Maneja el evento cuando se presiona el ratón en el tablero.
     *
     * @param e el evento de ratón
     */
    public void onMousePressed(MouseEvent e) {
        int fila = e.getY() / vista.getTamañoCelda().height;
        int columna = e.getX() / vista.getTamañoCelda().width;

        casillaInicial = modeloTablero.getCasilla(fila, columna);

        if (casillaInicial != null && casillaInicial.getUnidadOcupante() != null) {
            unidadSeleccionada = casillaInicial.getUnidadOcupante();
            casillasOriginales = new HashSet<>(unidadSeleccionada.getCasillasOcupadas());
            orientacionOriginal = unidadSeleccionada.getUnidad().getOrientacion();

            if (SwingUtilities.isRightMouseButton(e)) {
                // Rotar unidad
                System.out.println("Se presiono rotar");
                rotarUnidad(unidadSeleccionada);
            } else if (SwingUtilities.isLeftMouseButton(e)) {
                // Iniciar arrastre
                isDragging = true;
                vista.setIsDragging(true); // Notificar a la vista
                vista.setUnidadSeleccionada(unidadSeleccionada);
            }
        }
    }

    /**
     * Maneja el evento cuando se suelta el ratón en el tablero.
     *
     * @param e el evento de ratón
     */
    public void onMouseReleased(MouseEvent e) {
        if (isDragging && unidadSeleccionada != null) {
            int fila = e.getY() / vista.getTamañoCelda().height;
            int columna = e.getX() / vista.getTamañoCelda().width;

            ModeloCasilla casillaDestino = modeloTablero.getCasilla(fila, columna);

            // Mover unidad
            moverUnidad(unidadSeleccionada, casillaDestino);

            // Limpiar resaltado
            for (ModeloCasilla casilla : celdasResaltadas) {
                casilla.setHighlighted(false);
            }
            celdasResaltadas.clear();

            // Resetear estado de arrastre
            isDragging = false;
            unidadSeleccionada = null;
            casillaInicial = null;
            vista.setIsDragging(false); // Notificar a la vista
            vista.setUnidadSeleccionada(null);

            // Notificar al modelo que se actualizó
            modeloTablero.actualizarTablero();
        }
    }

    /**
     * Maneja el evento cuando se arrastra el ratón en el tablero.
     *
     * @param e el evento de ratón
     */
    public void onMouseDragged(MouseEvent e) {
        if (isDragging && unidadSeleccionada != null) {
            int fila = e.getY() / vista.getTamañoCelda().height;
            int columna = e.getX() / vista.getTamañoCelda().width;

            ModeloCasilla nuevaCasillaAncla = modeloTablero.getCasilla(fila, columna);

            if (nuevaCasillaAncla != null) {
                Set<ModeloCasilla> nuevasCasillas = calcularCasillasUnidad(nuevaCasillaAncla, unidadSeleccionada.getUnidad());

                // Limpiar resaltado anterior
                for (ModeloCasilla casilla : celdasResaltadas) {
                    casilla.setHighlighted(false);
                }
                celdasResaltadas.clear();

                // Resaltar nuevas celdas si la posición es válida
                if (nuevasCasillas != null && puedeColocarUnidad(nuevasCasillas, unidadSeleccionada)) {
                    for (ModeloCasilla casilla : nuevasCasillas) {
                        casilla.setHighlighted(true);
                        celdasResaltadas.add(casilla);
                    }
                }

                // Notificar al modelo que se actualizó
                modeloTablero.actualizarTablero();
            }
        }
    }

    /**
     * Rota la unidad especificada en el tablero.
     *
     * @param ubicacionUnidad la unidad a rotar
     */
    private void rotarUnidad(ModeloUbicacionUnidad ubicacionUnidad) {
        ModeloUnidad unidad = ubicacionUnidad.getUnidad();
        Orientacion nuevaOrientacion = (unidad.getOrientacion() == Orientacion.HORIZONTAL) ? Orientacion.VERTICAL : Orientacion.HORIZONTAL;
        unidad.setOrientacion(nuevaOrientacion);

        Set<ModeloCasilla> nuevasCasillas = calcularCasillasUnidad(casillaInicial, unidad);

        if (nuevasCasillas != null && puedeColocarUnidad(nuevasCasillas, ubicacionUnidad)) {
            // Actualizar modelo
            removerUnidad(ubicacionUnidad);

            // Limpiar adyacencias antes de colocar la unidad
            limpiarAdyacencias();

            ubicacionUnidad.setCasillasOcupadas(nuevasCasillas);
            colocarUnidad(ubicacionUnidad);

            // Remarcar adyacencias de todas las naves
            remarcarAdyacencias();
        } else {
            // No se puede rotar la unidad, restaurar orientación original
            unidad.setOrientacion(orientacionOriginal);
            colocarUnidad(ubicacionUnidad);
            remarcarAdyacencias();
        }
        // Notificar al modelo que se actualizó
        modeloTablero.actualizarTablero();
    }

    /**
     * Mueve la unidad especificada a una nueva casilla en el tablero.
     *
     * @param ubicacionUnidad la unidad a mover
     * @param nuevaCasillaAncla la nueva casilla ancla para la unidad
     */
    private void moverUnidad(ModeloUbicacionUnidad ubicacionUnidad, ModeloCasilla nuevaCasillaAncla) {
        ModeloUnidad unidad = ubicacionUnidad.getUnidad();

        Set<ModeloCasilla> nuevasCasillas = calcularCasillasUnidad(nuevaCasillaAncla, unidad);

        if (nuevasCasillas != null && puedeColocarUnidad(nuevasCasillas, ubicacionUnidad)) {
            // Actualizar modelo
            removerUnidad(ubicacionUnidad);

            // Limpiar adyacencias antes de colocar la unidad
            limpiarAdyacencias();

            ubicacionUnidad.setCasillasOcupadas(nuevasCasillas);
            colocarUnidad(ubicacionUnidad);

            // Remarcar adyacencias de todas las naves
            remarcarAdyacencias();
        } else {
            // No se puede mover la unidad, restaurar posición original
            colocarUnidad(ubicacionUnidad);
            remarcarAdyacencias();
        }
    }

    /**
     * Calcula las casillas que ocupará una unidad en base a una casilla ancla.
     *
     * @param casillaAncla la casilla ancla
     * @param unidad la unidad a colocar
     * @return un conjunto de casillas que ocupará la unidad, o null si no es
     * posible colocarla
     */
    private Set<ModeloCasilla> calcularCasillasUnidad(ModeloCasilla casillaAncla, ModeloUnidad unidad) {
        Set<ModeloCasilla> casillas = new HashSet<>();
        int x = casillaAncla.getCoordenada().getX();
        int y = casillaAncla.getCoordenada().getY();
        int tamaño = unidad.getTamaño();

        if (unidad.getOrientacion() == Orientacion.HORIZONTAL) {
            for (int i = 0; i < tamaño; i++) {
                ModeloCasilla casilla = modeloTablero.getCasilla(x, y + i);
                if (casilla != null) {
                    casillas.add(casilla);
                } else {
                    return null;
                }
            }
        } else {
            for (int i = 0; i < tamaño; i++) {
                ModeloCasilla casilla = modeloTablero.getCasilla(x + i, y);
                if (casilla != null) {
                    casillas.add(casilla);
                } else {
                    return null;
                }
            }
        }

        return casillas;
    }

    /**
     * Verifica si una unidad se puede colocar en un conjunto de casillas
     * especificado.
     *
     * @param casillas el conjunto de casillas
     * @param unidadActual la unidad que se desea colocar
     * @return true si la unidad se puede colocar, false en caso contrario
     */
    private boolean puedeColocarUnidad(Set<ModeloCasilla> casillas, ModeloUbicacionUnidad unidadActual) {
        for (ModeloCasilla casilla : casillas) {
            ModeloUbicacionUnidad unidadEnCasilla = casilla.getUnidadOcupante();
            if (unidadEnCasilla != null && !unidadEnCasilla.equals(unidadActual)) {
                // La casilla está ocupada por otra unidad
                return false;
            }
            if (casilla.esAdyacentePorOtraNave(unidadActual)) {
                // La casilla es adyacente a otra unidad
                return false;
            }
        }
        return true;
    }

    /**
     * Coloca una unidad en el tablero en las casillas especificadas.
     *
     * @param ubicacionUnidad la unidad a colocar
     */
    private void colocarUnidad(ModeloUbicacionUnidad ubicacionUnidad) {
        modeloTablero.getUnidades().add(ubicacionUnidad);
        for (ModeloCasilla casilla : ubicacionUnidad.getCasillasOcupadas()) {
            casilla.setUnidadOcupante(ubicacionUnidad);
        }
        marcarAdyacentes(ubicacionUnidad.getCasillasOcupadas(), ubicacionUnidad, true);
        // Notificar al modelo que se actualizó
        modeloTablero.actualizarTablero();
    }

    /**
     * Remueve una unidad del tablero.
     *
     * @param ubicacionUnidad la unidad a remover
     */
    private void removerUnidad(ModeloUbicacionUnidad ubicacionUnidad) {
        modeloTablero.getUnidades().remove(ubicacionUnidad);
        for (ModeloCasilla casilla : ubicacionUnidad.getCasillasOcupadas()) {
            casilla.setUnidadOcupante(null);
        }
        marcarAdyacentes(ubicacionUnidad.getCasillasOcupadas(), ubicacionUnidad, false);
        // Notificar al modelo que se actualizó
        modeloTablero.actualizarTablero();
    }

    /**
     * Limpia todas las adyacencias en el tablero.
     */
    private void limpiarAdyacencias() {
        ModeloCasilla[][] casillas = modeloTablero.getCasillas();
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                casillas[i][j].getNavesAdyacentes().clear();
            }
        }
    }

    /**
     * Remarca las adyacencias de todas las unidades en el tablero.
     */
    private void remarcarAdyacencias() {
        for (ModeloUbicacionUnidad ubicacionUnidad : modeloTablero.getUnidades()) {
            marcarAdyacentes(ubicacionUnidad.getCasillasOcupadas(), ubicacionUnidad, true);
        }
    }

    /**
     * Marca o desmarca las casillas adyacentes a un conjunto de casillas
     * especificado.
     *
     * @param casillas el conjunto de casillas
     * @param unidad la unidad para la cual se marcarán las adyacencias
     * @param marcar true para marcar, false para desmarcar
     */
    private void marcarAdyacentes(Set<ModeloCasilla> casillas, ModeloUbicacionUnidad unidad, boolean marcar) {
        for (ModeloCasilla casilla : casillas) {
            for (ModeloCasilla adyacente : obtenerAdyacentes(casilla)) {
                if (!casillas.contains(adyacente)) {
                    if (marcar) {
                        adyacente.agregarNaveAdyacente(unidad);
                    } else {
                        adyacente.eliminarNaveAdyacente(unidad);
                    }
                }
            }
        }
    }

    /**
     * Obtiene las casillas adyacentes a una casilla especificada.
     *
     * @param casilla la casilla para la cual se desean obtener las adyacencias
     * @return un conjunto de casillas adyacentes
     */
    private Set<ModeloCasilla> obtenerAdyacentes(ModeloCasilla casilla) {
        Set<ModeloCasilla> adyacentes = new HashSet<>();
        int x = casilla.getCoordenada().getX();
        int y = casilla.getCoordenada().getY();

        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < dx.length; i++) {
            ModeloCasilla adyacente = modeloTablero.getCasilla(x + dx[i], y + dy[i]);
            if (adyacente != null) {
                adyacentes.add(adyacente);
            }
        }

        return adyacentes;
    }

    /**
     * Inicializa las naves en el tablero en sus posiciones predeterminadas.
     */
    public int inicializarNaves(int x) {
        // Lista para almacenar las unidades creadas
        List<ModeloUnidad> unidades = new ArrayList<>();

        int Coloco = 0;
        // Definir las posiciones iniciales de las naves
        Map<ModeloUnidad, ModeloCasilla> posicionesIniciales = new HashMap<>();

        // Crear 2 naves de tamaño 4
        if (x == 0) {
            unidades.add(new ModeloUnidad(1, ModeloTipoUnidad.PORTAAVIONES.NOMBRE, Orientacion.HORIZONTAL, ModeloTipoUnidad.PORTAAVIONES.TAMANO));
            posicionesIniciales.put(unidades.get(0), modeloTablero.getCasilla(0, 0)); // Nave 1
            Coloco = 1;
        }
        if (x == 1) {
            unidades.add(new ModeloUnidad(2, ModeloTipoUnidad.PORTAAVIONES.NOMBRE, Orientacion.HORIZONTAL, ModeloTipoUnidad.PORTAAVIONES.TAMANO));
            posicionesIniciales.put(unidades.get(0), modeloTablero.getCasilla(0, 0)); // Nave 2 
            Coloco = 1;
        }

        // Crear 2 naves de tamaño 3
        if (x == 2) {
            unidades.add(new ModeloUnidad(3, ModeloTipoUnidad.CRUCERO.NOMBRE, Orientacion.HORIZONTAL, ModeloTipoUnidad.CRUCERO.TAMANO));
            posicionesIniciales.put(unidades.get(0), modeloTablero.getCasilla(0, 0)); // Nave 3
            Coloco = 1;
        }

        if (x == 3) {
            unidades.add(new ModeloUnidad(4, ModeloTipoUnidad.CRUCERO.NOMBRE, Orientacion.HORIZONTAL, ModeloTipoUnidad.CRUCERO.TAMANO));
            posicionesIniciales.put(unidades.get(0), modeloTablero.getCasilla(0, 0)); // Nave 4
            Coloco = 1;
        }
        // Crear 4 naves de tamaño 2
        if (x == 4) {
            unidades.add(new ModeloUnidad(5, ModeloTipoUnidad.SUBMARINO.NOMBRE, Orientacion.HORIZONTAL, ModeloTipoUnidad.SUBMARINO.TAMANO));
            posicionesIniciales.put(unidades.get(0), modeloTablero.getCasilla(0, 0)); // Nave 5
            Coloco = 1;
        }

        if (x == 5) {
            unidades.add(new ModeloUnidad(6, ModeloTipoUnidad.SUBMARINO.NOMBRE, Orientacion.HORIZONTAL, ModeloTipoUnidad.SUBMARINO.TAMANO));
            posicionesIniciales.put(unidades.get(0), modeloTablero.getCasilla(0, 0)); // Nave 6
            Coloco = 1;
        }

        if (x == 6) {
            unidades.add(new ModeloUnidad(7, ModeloTipoUnidad.SUBMARINO.NOMBRE, Orientacion.HORIZONTAL, ModeloTipoUnidad.SUBMARINO.TAMANO));
            posicionesIniciales.put(unidades.get(0), modeloTablero.getCasilla(0, 0)); // Nave 7
            Coloco = 1;
        }

        if (x == 7) {
            unidades.add(new ModeloUnidad(8, ModeloTipoUnidad.SUBMARINO.NOMBRE, Orientacion.HORIZONTAL, ModeloTipoUnidad.SUBMARINO.TAMANO));
            posicionesIniciales.put(unidades.get(0), modeloTablero.getCasilla(0, 0)); // Nave 8
            Coloco = 1;
        }

        // Crear 3 naves de tamaño 1
        if (x == 8) {
            unidades.add(new ModeloUnidad(9, ModeloTipoUnidad.BARCO.NOMBRE, Orientacion.HORIZONTAL, ModeloTipoUnidad.BARCO.TAMANO));
            posicionesIniciales.put(unidades.get(0), modeloTablero.getCasilla(0, 0)); // Nave 9
            Coloco = 1;
        }

        if (x == 9) {
            unidades.add(new ModeloUnidad(10, ModeloTipoUnidad.BARCO.NOMBRE, Orientacion.HORIZONTAL, ModeloTipoUnidad.BARCO.TAMANO));
            posicionesIniciales.put(unidades.get(0), modeloTablero.getCasilla(0, 0)); // Nave 10
            Coloco = 1;
        }

        if (x == 10) {
            unidades.add(new ModeloUnidad(11, ModeloTipoUnidad.BARCO.NOMBRE, Orientacion.HORIZONTAL, ModeloTipoUnidad.BARCO.TAMANO));
            posicionesIniciales.put(unidades.get(0), modeloTablero.getCasilla(0, 0)); // Nave 11
            Coloco = 1;
        }

        // Colocar las naves en el tablero
        for (ModeloUnidad unidad : unidades) {
            ModeloCasilla casillaAncla = posicionesIniciales.get(unidad);

            Set<ModeloCasilla> casillasOcupadas = calcularCasillasUnidad(casillaAncla, unidad);

            if (casillasOcupadas != null) {
                // Creamos la ubicación de la unidad antes de verificar si puede ser colocada
                ModeloUbicacionUnidad ubicacionUnidad = new ModeloUbicacionUnidad(unidad, casillasOcupadas);

                if (puedeColocarUnidad(casillasOcupadas, ubicacionUnidad)) {
                    colocarUnidad(ubicacionUnidad);
                } else {
                    // Manejar el caso en que no se pueda colocar la unidad
                    System.out.println("No se pudo colocar la unidad: " + unidad.getNumNave());
                    Coloco = 0;
                }
            } else {
                System.out.println("No se pudo calcular las casillas para la unidad: " + unidad.getNumNave());
            }
        }

        // Actualizar la vista
        vista.actualizarVista();
        return Coloco;
    }

    /**
     * Obtiene el modelo del tablero del juego.
     *
     * @return el modelo del tablero
     */
    public ModeloTablero getModeloTablero() {
        return modeloTablero;
    }

    /**
     * Envía un ataque al servidor en las coordenadas especificadas.
     *
     * @param fila la coordenada de la fila
     * @param columna la coordenada de la columna
     */
    public void enviarAtaque(int fila, int columna) {
        // envia el ataque al servidor
        ConexionCliente.getInstance().atacar(fila, columna);
    }

    /**
     * Establece el listener para los eventos de ataque.
     *
     * @param listener el listener de ataque
     */
    public void setAtaqueListener(AtaqueListener listener) {
        this.ataqueListener = listener;
    }

    /**
     * Obtiene el listener para los eventos de ataque.
     *
     * @return el listener de ataque
     */
    public AtaqueListener getAtaqueListener() {
        return ataqueListener;
    }

}
