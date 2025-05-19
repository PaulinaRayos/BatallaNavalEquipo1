/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import Modelo.ModeloCasilla;
import Modelo.ModeloCoordenada;
import Modelo.ModeloJugador;
import Modelo.ModeloUnidad;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class UnidadBO {

    // Método para obtener las casillas a partir de una lista de coordenadas recibidas
    public Map<ModeloCasilla, Boolean> obtenerCoordenadas(Map<String, Object> data) {
        Map<ModeloCasilla, Boolean> casillas = new HashMap<>();

        // Se espera que las coordenadas lleguen como una lista de mapas con claves "x" y "y"
        List<Map<String, Integer>> coordenadas = (List<Map<String, Integer>>) data.get("coordenadas");

        for (Map<String, Integer> coord : coordenadas) {
            int x = coord.get("x"); // Obtener coordenada X
            int y = coord.get("y"); // Obtener coordenada Y

            // Crear casilla con las coordenadas y agregarla al mapa
            ModeloCasilla casilla = new ModeloCasilla(new ModeloCoordenada(x, y));
            casillas.put(casilla, Boolean.TRUE);
        }

        return casillas; // Devolver las casillas marcadas
    }

    // Método para buscar una unidad de un jugador a partir del número de nave
    ModeloUnidad obtenerUnidadPorNumNave(ModeloJugador jugador, int numNave) {
        for (ModeloUnidad unidad : jugador.getUnidades()) {
            if (unidad.getNumNave() == numNave) {
                return unidad; // Unidad encontrada
            }
        }
        return null; // Si no se encuentra la unidad
    }
}
