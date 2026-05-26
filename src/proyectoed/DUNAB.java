package proyectoed;

import java.io.Serializable;

/**
 * @authors Tomás Reyes, Juan Mateus, Santiago Rey, David Barbosa
 */
public class DUNAB implements Serializable {
    private static final long serialVersionUID = 1L; 

    private String motivo;      
    private int cantidad;       
    private String fechaRegistro;

    public DUNAB(String motivo, int cantidad, String fechaRegistro) {
        this.motivo = motivo;
        this.cantidad = cantidad;
        this.fechaRegistro = fechaRegistro;
    }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    @Override
    public String toString() {
        String tipo = (cantidad >= 0) ? " GANADO [+" : " GASTADO [";
        return "Movimiento: " + motivo + " |" + tipo + cantidad + " DUNAB] | Fecha: " + fechaRegistro;
    }
}
