package proyectoed.modelos;

import java.io.Serializable;

/**
 * @authors Tomás Reyes, Juan Mateus, Santiago Rey, David Barbosa
 */
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private int costo;
    private String categoria;
    private int stock;

    public Producto(String nombre, int costo, String categoria, int stock) {
        this.nombre = nombre;
        this.costo = costo;
        this.categoria = categoria;
        this.stock = stock;
    }

    public String getNombre() { return nombre; }
    public int getCosto() { return costo; }
    public String getCategoria() { return categoria; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
