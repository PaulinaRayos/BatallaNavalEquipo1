/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package vistaModelo;

/**
 * Interfaz que define un listener para eventos de ataque. Debe ser implementada
 * por clases que necesiten reaccionar cuando se realiza un ataque.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public interface AtaqueListener {

    /**
     * Método llamado cuando se ha realizado un ataque.
     */
    void enAtaqueRealizado();
}
