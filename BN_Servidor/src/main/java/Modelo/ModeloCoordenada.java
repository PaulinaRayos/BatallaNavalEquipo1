package Modelo;

/**
 * Representa una coordenada en el tablero con valores X e Y. Sirve para ubicar
 * posiciones dentro del tablero de juego.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ModeloCoordenada {

    /**
     * Coordenada en el eje X (fila).
     */
    private int x;

    /**
     * Coordenada en el eje Y (columna).
     */
    private int y;

    /**
     * Constructor que inicializa la coordenada con valores X y Y.
     *
     * @param x valor en el eje X (fila)
     * @param y valor en el eje Y (columna)
     */
    public ModeloCoordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Obtiene la coordenada X.
     *
     * @return valor de X
     */
    public int getX() {
        return x;
    }

    /**
     * Establece la coordenada X.
     *
     * @param x nuevo valor para X
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Obtiene la coordenada Y.
     *
     * @return valor de Y
     */
    public int getY() {
        return y;
    }

    /**
     * Establece la coordenada Y.
     *
     * @param y nuevo valor para Y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Representación en texto de la coordenada.
     *
     * @return String con el formato ModeloCoordenada{x=val, y=val}
     */
    @Override
    public String toString() {
        return "ModeloCoordenada{" + "x=" + x + ", y=" + y + '}';
    }
}
