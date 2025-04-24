package Modelo;
/**
 * 
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */

public class ModeloCoordenada {
    private int x;
    private int y;

    public ModeloCoordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "ModeloCoordenada{" + "x=" + x + ", y=" + y + '}';
    }
    
    
}
