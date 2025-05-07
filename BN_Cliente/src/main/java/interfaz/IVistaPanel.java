/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaz;

import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author pauli
 */
public interface IVistaPanel {
     /**
     * Dibuja los elementos gráficos en el contexto proporcionado.
     *
     * @param g el contexto gráfico donde se dibujarán los elementos
     */
    public void dibujar(Graphics g);
    
    /**
     * Define las acciones que realizarán los componentes interactivos.
     */
    public void accionesComponentes();
    
    /**
     * Crea e inicializa los componentes gráficos del panel.
     */
    public void crearComponentes();
    
    /**
     * Quita todos los componentes gráficos del panel.
     */
    public void quitarComponentes();
    
}

   