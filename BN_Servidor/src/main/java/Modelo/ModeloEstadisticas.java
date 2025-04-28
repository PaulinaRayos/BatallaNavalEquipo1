package Modelo;


public class ModeloEstadisticas {
    private int disparosExitosos;
    private int movimientos;
    private int turnos;

    public ModeloEstadisticas() {
    }

    public ModeloEstadisticas(int disparosExitosos, int movimientos, int turnos) {
        this.disparosExitosos = disparosExitosos;
        this.movimientos = movimientos;
        this.turnos = turnos;
    }

    public int getDisparosExitosos() {
        return disparosExitosos;
    }

    public void setDisparosExitosos(int disparosExitosos) {
        this.disparosExitosos = disparosExitosos;
    }

    public int getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(int movimientos) {
        this.movimientos = movimientos;
    }

    public int getTurnos() {
        return turnos;
    }

    public void setTurnos(int turnos) {
        this.turnos = turnos;
    }
    
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
