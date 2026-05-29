package proyectoed.modelos;

import java.io.Serializable;

/**
 *
 * @authors Tomás Reyes, Juan Mateus, Santiago Rey, David Barbosa
 */
public class Encuentro implements Serializable {
    private static final long serialVersionUID = 1L;
    private String actividad;
    private String descripcion;
    private String fecha;
    private String hora;

    public Encuentro(String actividad, String descripcion, String fecha, String hora) {
        this.actividad = actividad;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getActividad() { return actividad; }
    public void setActividad(String actividad) { this.actividad = actividad; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    @Override
    public String toString() {
        return "Actividad: " + actividad + " | Descripción: " + descripcion + " | Fecha: " + fecha + " | Hora: " + hora;
    }
}