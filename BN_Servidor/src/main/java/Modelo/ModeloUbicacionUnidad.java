package Modelo;

import java.util.Map;

/**
 * Clase que representa la ubicación de una unidad en el tablero, incluyendo la
 * unidad y las casillas que ocupa.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ModeloUbicacionUnidad {

    /**
     * La unidad asociada a esta ubicación.
     */
    private ModeloUnidad unidad;

    /**
     * Mapa que asocia cada casilla con un valor booleano, indicando si ha sido
     * atacada (true) o no (false).
     */
    private Map<ModeloCasilla, Boolean> casillas;

    /**
     * Constructor que inicializa la unidad y sus casillas asociadas.
     *
     * @param unidad la unidad a ubicar
     * @param casillas el mapa de casillas ocupadas por la unidad
     */
    public ModeloUbicacionUnidad(ModeloUnidad unidad, Map<ModeloCasilla, Boolean> casillas) {
        this.unidad = unidad;
        this.casillas = casillas;
    }

    /**
     * Añade una casilla al mapa de ubicación de la unidad.
     *
     * @param casilla la casilla a añadir
     * @param bool true si la casilla ha sido atacada, false en caso contrario
     */
    public void addCasilla(ModeloCasilla casilla, Boolean bool) {
        this.casillas.put(casilla, bool);
    }

    /**
     * Obtiene la unidad asociada a esta ubicación.
     *
     * @return la unidad
     */
    public ModeloUnidad getUnidad() {
        return unidad;
    }

    /**
     * Establece la unidad asociada a esta ubicación.
     *
     * @param unidad la nueva unidad
     */
    public void setUnidad(ModeloUnidad unidad) {
        this.unidad = unidad;
    }

    /**
     * Obtiene el mapa de casillas asociadas a la unidad.
     *
     * @return el mapa de casillas
     */
    public Map<ModeloCasilla, Boolean> getCasillas() {
        return casillas;
    }

    /**
     * Limpia todas las casillas del mapa.
     */
    public void limipiarCasillas() {
        this.casillas.clear();
    }

    /**
     * Establece el mapa de casillas para esta unidad.
     *
     * @param casillas el nuevo mapa de casillas
     */
    public void setCasillas(Map<ModeloCasilla, Boolean> casillas) {
        this.casillas = casillas;
    }

    /**
     * Devuelve una representación en cadena de la ubicación de la unidad.
     *
     * @return una cadena con la unidad y sus casillas
     */
    @Override
    public String toString() {
        return "UbicacionUnidad{" + "unidad=" + unidad + ", casillas=" + casillas + '}';
    }

}
