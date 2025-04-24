package Modelo;

/**
 * 
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class ModeloCasilla {
    private ModeloCoordenada coordenada;

    public ModeloCasilla(ModeloCoordenada coordenada) {
        this.coordenada = coordenada;
    }

    public ModeloCoordenada getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(ModeloCoordenada coordenada) {
        this.coordenada = coordenada;
    }

    @Override
    public String toString() {
        return "ModeloCasilla{" + "coordenada=" + coordenada + '}';
    }
    
    
}
