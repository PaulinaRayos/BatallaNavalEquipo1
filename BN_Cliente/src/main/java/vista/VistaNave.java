/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.Graphics;
import javax.swing.JLabel;

/**
 * Clase que representa visualmente una nave en el tablero. Extiende JLabel para
 * poder integrarse fácilmente en la interfaz gráfica.
 *
 * Cada nave tiene un número identificador, una vida máxima y una vida actual.
 * El color de la nave cambia dinámicamente según su estado de daño: - Sin daño
 * (vida completa) - Dañada (vida parcial) - Destruida (vida cero)
 *
 * El método {@code paintComponent} es sobreescrito para reflejar visualmente
 * estos estados.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class VistaNave extends JLabel {

    /**
     * Número de identificación de la nave.
     */
    private final int numNave;

    /**
     * Vida máxima que puede tener la nave.
     */
    private final int vidaMaxima;

    /**
     * Vida actual de la nave.
     */
    private int vida;

    /**
     * Constructor de la clase VistaNave. Inicializa los valores de número de
     * nave, vida máxima y vida actual.
     *
     * @param numNave Número identificador único de la nave.
     * @param vidaMaxima Valor máximo de vida que puede tener esta nave.
     */
    public VistaNave(int numNave, int vidaMaxima) {
        this.numNave = numNave;
        this.vidaMaxima = vidaMaxima;
        this.vida = vidaMaxima;
    }

    /**
     * Devuelve el número de identificación de la nave.
     *
     * @return Número de la nave.
     */
    public int getNumNave() {
        return numNave;
    }

    /**
     * Devuelve la vida actual de la nave.
     *
     * @return Valor actual de vida de la nave.
     */
    public int getVida() {
        return vida;
    }

    /**
     * Establece la vida actual de la nave.
     *
     * @param vida Nuevo valor de vida de la nave.
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Método sobrescrito que se encarga de pintar la nave con un color
     * diferente según su estado de vida: - Verde: sin daño - Amarillo: dañada -
     * Rojo: destruida
     *
     * @param g Objeto Graphics utilizado para dibujar sobre el componente.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.vida == vidaMaxima) {
            g.setColor(VistaUtilidades.COLOR_UNIDAD_SIN_DANO);
        } else if (this.vida < vidaMaxima && this.vida > 0) {
            g.setColor(VistaUtilidades.COLOR_UNIDAD_DANADA);
        } else if (this.vida == 0) {
            g.setColor(VistaUtilidades.COLOR_UNIDAD_DESTRUIDA);
        }

        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
