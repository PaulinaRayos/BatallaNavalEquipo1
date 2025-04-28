/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.util.List;
/**
 *
 * @author pauli
 */
public class ModeloJugador {
    private String nombre;
    private ModeloEstadisticas estadisticas;
    private List<ModeloUnidad> unidades;
    private String id;
    private boolean listo;
    private boolean quiereRevancha;

    public ModeloJugador(String nombre, ModeloEstadisticas estadisticas, List<ModeloUnidad> unidades) {
        this.nombre = nombre;
       this.estadisticas = estadisticas;
       this.unidades = unidades;
        this.listo = false;
        this.quiereRevancha = false;
    }

    public ModeloJugador(String nombre, ModeloEstadisticas estadisticas) {
        this.nombre = nombre;
        this.estadisticas = estadisticas;
    }

    public ModeloJugador() {
    }

    public ModeloJugador(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ModeloEstadisticas getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(ModeloEstadisticas estadisticas) {
        this.estadisticas = estadisticas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ModeloUnidad> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<ModeloUnidad> unidades) {
        this.unidades = unidades;
    }

    public void addUnidad(ModeloUnidad unidad) {
        this.unidades.add(unidad);
    }

    public boolean isListo() {
        return listo;
    }

    public void setListo(boolean listo) {
        this.listo = listo;
    }
    
    public boolean isQuiereRevancha() {
        return quiereRevancha;
    }

    public void setQuiereRevancha(boolean quiereRevancha) {
        this.quiereRevancha = quiereRevancha;
    }

}
