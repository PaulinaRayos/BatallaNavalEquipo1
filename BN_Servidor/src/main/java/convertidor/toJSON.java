/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package convertidor;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author pauli
 */
public class toJSON {
        /**
     * Convierte los argumentos proporcionados en un Map de tipo clave-valor.
     * Los argumentos deben ser proporcionados en pares, donde el primer argumento
     * de cada par es la clave (de tipo String) y el segundo es el valor.
     *
     * @param args Los pares clave-valor a convertir en un Map.
     * @return Un Map con los datos proporcionados.
     * @throws IllegalArgumentException si una clave no es de tipo String.
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
