/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package vista;

import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Interfaz que define los métodos esenciales para una vista con panel gráfico.
 *
 * Esta interfaz debe ser implementada por todas las vistas que manejen
 * componentes gráficos y que necesiten dibujar, crear, configurar acciones y
 * quitar dichos componentes.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public interface IVistaPanel {

    /**
     * Dibuja los elementos gráficos en el contexto proporcionado.
     *
     * @param g El contexto gráfico donde se dibujarán los elementos.
     */
    public void dibujar(Graphics g);

    /**
     * Define las acciones que realizarán los componentes interactivos.
     *
     * Normalmente se agregan listeners y manejadores de eventos aquí.
     */
    public void accionesComponentes();

    /**
     * Crea e inicializa los componentes gráficos del panel.
     *
     * Aquí se instancian y configuran los componentes visuales.
     */
    public void crearComponentes();

    /**
     * Quita todos los componentes gráficos del panel.
     *
     * Se utiliza para limpiar la vista o preparar el panel para otra pantalla.
     */
    public void quitarComponentes();

}
