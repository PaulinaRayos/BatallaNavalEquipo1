package bo;


/**
 * Representa las estadísticas de un jugador durante una partida.
 * Incluye la cantidad de disparos exitosos, movimientos realizados y turnos jugados.
 * 
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ModeloEstadisticas {

    /**
     * Número de disparos que han sido exitosos (impactaron una unidad enemiga).
     */
    private int disparosExitosos;

    /**
     * Número de movimientos realizados por el jugador.
     */
    private int movimientos;

    /**
     * Número total de turnos jugados por el jugador.
     */
    private int turnos;

    /**
     * Constructor por defecto.
     */
    public ModeloEstadisticas() {
    }

    /**
     * Constructor con parámetros para inicializar todas las estadísticas.
     * 
     * @param disparosExitosos número de disparos exitosos
     * @param movimientos número de movimientos realizados
     * @param turnos número de turnos jugados
     */
    public ModeloEstadisticas(int disparosExitosos, int movimientos, int turnos) {
        this.disparosExitosos = disparosExitosos;
        this.movimientos = movimientos;
        this.turnos = turnos;
    }

    /**
     * Devuelve el número de disparos exitosos.
     * 
     * @return disparos exitosos
     */
    public int getDisparosExitosos() {
        return disparosExitosos;
    }

    /**
     * Establece el número de disparos exitosos.
     * 
     * @param disparosExitosos nuevo valor de disparos exitosos
     */
    public void setDisparosExitosos(int disparosExitosos) {
        this.disparosExitosos = disparosExitosos;
    }

    /**
     * Devuelve el número de movimientos realizados.
     * 
     * @return número de movimientos
     */
    public int getMovimientos() {
        return movimientos;
    }

    /**
     * Establece el número de movimientos realizados.
     * 
     * @param movimientos nuevo número de movimientos
     */
    public void setMovimientos(int movimientos) {
        this.movimientos = movimientos;
    }

    /**
     * Devuelve el número de turnos jugados.
     * 
     * @return número de turnos
     */
    public int getTurnos() {
        return turnos;
    }

    /**
     * Establece el número de turnos jugados.
     * 
     * @param turnos nuevo número de turnos
     */
    public void setTurnos(int turnos) {
        this.turnos = turnos;
    }

    /**
     * Devuelve una representación en forma de cadena de las estadísticas.
     * 
     * @return cadena con los valores de disparos exitosos, movimientos y turnos
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Estadisticas{");
        sb.append("disparosExitosos=").append(disparosExitosos);
        sb.append(", movimientos=").append(movimientos);
        sb.append(", turnos=").append(turnos);
        sb.append('}');
        return sb.toString();
    }
}
