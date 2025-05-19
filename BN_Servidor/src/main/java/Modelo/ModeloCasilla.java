package Modelo;

/**
 * Representa una casilla en el tablero, definida por una coordenada. Cada
 * casilla tiene una posición única identificada por un objeto ModeloCoordenada.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ModeloCasilla {

    /**
     * Coordenada que identifica la posición de la casilla en el tablero.
     */
    private ModeloCoordenada coordenada;

    /**
     * Constructor que inicializa la casilla con una coordenada dada.
     *
     * @param coordenada la posición en el tablero
     */
    public ModeloCasilla(ModeloCoordenada coordenada) {
        this.coordenada = coordenada;
    }

    /**
     * Obtiene la coordenada de la casilla.
     *
     * @return la coordenada actual de la casilla
     */
    public ModeloCoordenada getCoordenada() {
        return coordenada;
    }

    /**
     * Establece la coordenada de la casilla.
     *
     * @param coordenada nueva coordenada para la casilla
     */
    public void setCoordenada(ModeloCoordenada coordenada) {
        this.coordenada = coordenada;
    }

    /**
     * Representación en texto de la casilla con su coordenada.
     *
     * @return cadena con la información de la casilla
     */
    @Override
    public String toString() {
        return "ModeloCasilla{" + "coordenada=" + coordenada + '}';
    }
}
