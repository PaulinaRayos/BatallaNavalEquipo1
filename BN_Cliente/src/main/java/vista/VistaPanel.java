/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import vistaModelo.Juego;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Panel principal del juego donde se dibujan y gestionan los componentes
 * visuales. Permite agregar y quitar elementos gráficos y actualiza su
 * visualización según el estado del juego.
 *
 * @author pauli
 * @author sebastian
 * @author agustin
 * @author joaquin
 */
public class VistaPanel extends JPanel {

    /**
     * Referencia al objeto Juego.
     */
    private Juego juego;

    /**
     * Constructor de la clase VistaPanel que recibe una referencia al juego.
     * Inicializa los manejadores de eventos y configura el tamaño del panel.
     *
     * @param juego Referencia al objeto Juego.
     */
    public VistaPanel(Juego juego) {
        // Establece la referencia al juego
        this.juego = juego;
        // Configura el tamaño del panel
        setPanelSize();
        requestFocus();
        setLayout(null);
    }

    /**
     * Método privado para establecer el tamaño del panel según las dimensiones
     * del juego.
     */
    private void setPanelSize() {
        // Dimensiones del panel según las del juego
        Dimension size = new Dimension(juego.GAME_ANCHO, juego.GAME_ALTO);
        // Establece el tamaño preferido del panel
        setPreferredSize(size);
        // Muestra las dimensiones en la consola
        System.out.println("Size: " + Juego.GAME_ANCHO + " : " + Juego.GAME_ALTO);
    }

    /**
     * Agrega un componente al panel en la posición y tamaño especificados.
     *
     * @param componente el componente a agregar
     * @param x la coordenada x de la posición del componente
     * @param y la coordenada y de la posición del componente
     * @param ancho el ancho del componente
     * @param alto el alto del componente
     */
    public void agregarComponente(JComponent componente, int x, int y, int ancho, int alto) {
        componente.setBounds(x, y, ancho, alto);
        add(componente);
        revalidate();
        repaint();
    }

    /**
     * Quita un componente del panel.
     *
     * @param componente el componente a quitar
     */
    public void quitarComponente(JComponent componente) {
        remove(componente);
        revalidate();
        repaint();
    }

    /**
     * Método para actualizar el juego (a implementar según las necesidades).
     */
    public void updateJuego() {

    }

    /**
     * Método para dibujar en el panel. Llama al método de renderizado del juego
     * y pasa el contexto gráfico.
     *
     * @param g Objeto Graphics para dibujar.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Llama al método de renderizado del juego y pasa el contexto gráfico
        juego.renderizar(g);
    }

    /**
     * Método para obtener la referencia al juego.
     *
     * @return Referencia al objeto Juego.
     */
    public Juego getJuego() {
        return juego;
    }

}
