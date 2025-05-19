/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import Modelo.ModeloTipoUnidad;
import Modelo.ModeloUnidad;

/**
 * Clase que se encarga de crear unidades según el tipo especificado.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class UnidadFactory {

    // Método estático que crea una unidad en base al tipo proporcionado
    public static ModeloUnidad crearUnidad(String tipo) {
        // Si el tipo es "BARCO", se crea una unidad tipo BARCO
        if (ModeloTipoUnidad.BARCO.NOMBRE.equals(tipo)) {
            return new ModeloUnidad(ModeloTipoUnidad.BARCO.NOMBRE, ModeloTipoUnidad.BARCO.VIDA);
        } // Si el tipo es "SUBMARINO", se crea una unidad tipo SUBMARINO
        else if (ModeloTipoUnidad.SUBMARINO.NOMBRE.equals(tipo)) {
            return new ModeloUnidad(ModeloTipoUnidad.SUBMARINO.NOMBRE, ModeloTipoUnidad.SUBMARINO.VIDA);
        } // Si el tipo es "CRUCERO", se crea una unidad tipo CRUCERO
        else if (ModeloTipoUnidad.CRUCERO.NOMBRE.equals(tipo)) {
            return new ModeloUnidad(ModeloTipoUnidad.CRUCERO.NOMBRE, ModeloTipoUnidad.CRUCERO.VIDA);
        } // Si el tipo es "PORTAAVIONES", se crea una unidad tipo PORTAAVIONES
        else if (ModeloTipoUnidad.PORTAAVIONES.NOMBRE.equals(tipo)) {
            return new ModeloUnidad(ModeloTipoUnidad.PORTAAVIONES.NOMBRE, ModeloTipoUnidad.PORTAAVIONES.VIDA);
        }

        // Si el tipo no coincide con ninguno, se retorna null
        return null;
    }
}
