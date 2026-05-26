package proyectoed;

import java.io.Serializable;

/**
 * @authors Tomás Reyes, Juan Mateus, Santiago Rey, David Barbosa
 */
public class Transaccion implements Serializable {
    private static final long serialVersionUID = 1L;
    private String concepto;
    private int cantidad;
    private String fecha;
    private String tipo;

    public Transaccion(String concepto, int cantidad, String fecha, String tipo) {
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public String getConcepto() { return concepto; }
    public int getCantidad() { return cantidad; }
    public String getFecha() { return fecha; }
    public String getTipo() { return tipo; }
}
