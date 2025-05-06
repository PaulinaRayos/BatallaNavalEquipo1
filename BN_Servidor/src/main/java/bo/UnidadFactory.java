/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import Modelo.ModeloTipoUnidad;
import Modelo.ModeloUnidad;

/**
 *
 * @author pauli
 */
public class UnidadFactory {
    public static ModeloUnidad crearUnidad(String tipo) {
        if (ModeloTipoUnidad.BARCO.NOMBRE.equals(tipo)) {
            return new ModeloUnidad(ModeloTipoUnidad.BARCO.NOMBRE, ModeloTipoUnidad.BARCO.VIDA);
        } else if (ModeloTipoUnidad.SUBMARINO.NOMBRE.equals(tipo)) {
            return new ModeloUnidad(ModeloTipoUnidad.SUBMARINO.NOMBRE, ModeloTipoUnidad.SUBMARINO.VIDA);
        } else if (ModeloTipoUnidad.CRUCERO.NOMBRE.equals(tipo)) {
            return new ModeloUnidad(ModeloTipoUnidad.CRUCERO.NOMBRE, ModeloTipoUnidad.CRUCERO.VIDA);
        } else if (ModeloTipoUnidad.PORTAAVIONES.NOMBRE.equals(tipo)) {
            return new ModeloUnidad(ModeloTipoUnidad.PORTAAVIONES.NOMBRE, ModeloTipoUnidad.PORTAAVIONES.VIDA);
        }
        // No se encontro el tipo
        return null;
    }
}
