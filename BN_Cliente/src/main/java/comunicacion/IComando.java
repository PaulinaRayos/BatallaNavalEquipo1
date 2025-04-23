/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package comunicacion;

import java.util.Map;

/**
 *
 * @author pauli
 */
public interface IComando {
    
    void execute(Map<String, Object> mensaje);
}
