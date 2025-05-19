/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 * Clase que define constantes para los diferentes tipos de unidades del juego,
 * incluyendo su nombre, tamaño y vida inicial.
 *
 * Esta clase utiliza clases internas estáticas para representar cada tipo de
 * unidad.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ModeloTipoUnidad {

    /**
     * Constantes para el tipo de unidad Barco.
     */
    public static class BARCO {

        public static final String NOMBRE = "Barco";
        public static final int TAMANO = 1;
        public static final int VIDA = 1;
    }

    /**
     * Constantes para el tipo de unidad Submarino.
     */
    public static class SUBMARINO {

        public static final String NOMBRE = "Submarino";
        public static final int TAMANO = 2;
        public static final int VIDA = 2;
    }

    /**
     * Constantes para el tipo de unidad Crucero.
     */
    public static class CRUCERO {

        public static final String NOMBRE = "Crucero";
        public static final int TAMANO = 3;
        public static final int VIDA = 3;
    }

    /**
     * Constantes para el tipo de unidad Portaaviones.
     */
    public static class PORTAAVIONES {

        public static final String NOMBRE = "Portaaviones";
        public static final int TAMANO = 4;
        public static final int VIDA = 4;
    }
}
