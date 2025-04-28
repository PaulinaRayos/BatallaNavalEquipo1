package Modelo;

import enums.EstadoUnidad;
import enums.Orientacion;


public class ModeloUnidad {
    private int numNave;
    private final String nombre;
    private final int tamanio;
    private int vida;
    private int vidaMax;
    private Orientacion orientacion;
    private EstadoUnidad estado;

    public ModeloUnidad(String nombre, int tamanio) {
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.vida = tamanio;
        this.vidaMax = vida;
        this.estado = estado.INTACTA;
    }

    public void resetearVida() {
        this.vida = vidaMax;
    }
    
    public int getNumNave() {
        return numNave;
    }

    public void setNumNave(int numNave) {
        this.numNave = numNave;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getVidaMax() {
        return vidaMax;
    }

    public void setVidaMax(int vidaMax) {
        this.vidaMax = vidaMax;
    }

    public Orientacion getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(Orientacion orientacion) {
        this.orientacion = orientacion;
    }

    public EstadoUnidad getEstado() {
        return estado;
    }

    public void setEstado(EstadoUnidad estado) {
        this.estado = estado;
        this.estado = EstadoUnidad.INTACTA;
    }
    
    public int getTamanio() {
        return tamanio;
    }

    @Override
    public String toString() {
        return "ModeloUnidad{" + "numNave=" + numNave + ", nombre=" + nombre + '}';
    }
    
    
    
}
