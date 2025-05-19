/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package convertidor;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class toJSON {

    /**
     * Crea un Map<String, Object> a partir de pares de argumentos
     * proporcionados. Se espera que los argumentos estén en orden: clave1,
     * valor1, clave2, valor2, ...
     *
     * @param args Lista variable de argumentos donde los índices pares deben
     * ser Strings (claves) y los índices impares los valores asociados.
     * @return Un mapa que asocia cada clave con su valor correspondiente.
     * @throws IllegalArgumentException si alguna clave no es una cadena de
     * texto (String).
     */
    public static Map<String, Object> dataToJSON(Object... args) {
        Map<String, Object> data = new HashMap<>();

        for (int i = 0; i < args.length - 1; i += 2) {
            if (args[i] instanceof String) {
                String key = (String) args[i];
                Object value = args[i + 1];
                data.put(key, value);
            } else {
                throw new IllegalArgumentException("Clave debe ser de tipo String");
            }
        }

        return data;
    }
}
