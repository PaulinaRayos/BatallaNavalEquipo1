/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prueba;

import vista.VistaBienvenida;
import vistaModelo.Juego;

/**
 * Clase principal para ejecutar el juego. Contiene el método main que inicia la
 * ejecución del programa creando una instancia de la clase Juego y llamando a
 * su método run.
 *
 * @author pauli
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class Prueba {

    /**
     * Método principal que ejecuta la aplicación.
     *
     * @param args los argumentos de línea de comando (no usados)
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Juego juego = new Juego();
        juego.run();
    }
}
