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
 *
 * @author pauli
 */
public class UnidadBO {
    public Map<ModeloCasilla, Boolean> obtenerCoordenadas(Map<String, Object> data) {
        Map<ModeloCasilla, Boolean> casillas = new HashMap<>();

        // Suponiendo que las coordenadas se envían como una lista de mapas
        // donde cada mapa tiene las claves "x" y "y"
        List<Map<String, Integer>> coordenadas = (List<Map<String, Integer>>) data.get("coordenadas");

        for (Map<String, Integer> coord : coordenadas) {
            int x = coord.get("x");
            int y = coord.get("y");
            ModeloCasilla casilla = new ModeloCasilla(new ModeloCoordenada(x, y));
            casillas.put(casilla, Boolean.TRUE);
        }

        return casillas;
    }

    ModeloUnidad obtenerUnidadPorNumNave(ModeloJugador jugador, int numNave) {
        for (ModeloUnidad unidad : jugador.getUnidades()) {
            if (unidad.getNumNave() == numNave) {
                return unidad;
            }
        }
        return null; // Si no se encuentra la unidad
    }
}
