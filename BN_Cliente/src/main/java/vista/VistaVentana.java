/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JFrame;

/**
 * Clase que representa la ventana principal de la aplicación Batalla Naval.
 * Esta ventana contiene un panel de vista específico y configura la apariencia
 * y el comportamiento inicial de la ventana, incluyendo el manejo del cierre,
 * tamaño, visibilidad y eventos de enfoque.
 *
 * @author pauli
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class VistaVentana extends JFrame {

    /**
     * Constructor que inicializa la ventana con el panel proporcionado.
     * Configura el título, tamaño, comportamiento al cerrar y visibilidad.
     *
     * @param panel el VistaPanel que se agrega a la ventana
     */
    public VistaVentana(VistaPanel panel) {
        // Configura la acción al cerrar la ventana para terminar la aplicación
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Título de la ventana
        this.setTitle("Batalla Naval");
        // Agrega el panel al JFrame
        this.add(panel);
        // No permite redimensionar la ventana
        this.setResizable(false);
        // Ajusta el tamaño de la ventana al tamaño del contenido
        this.pack();
        // Centra la ventana en la pantalla
        this.setLocationRelativeTo(null);
        // Hace visible la ventana
        this.setVisible(true);

        // Escucha eventos de enfoque de la ventana
        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                // Se puede implementar acción cuando la ventana gana enfoque
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                // Se puede implementar acción cuando la ventana pierde enfoque
                // Ejemplo (comentado): panel.getJuego().windowFocusLost();
            }
        });
    }
}
