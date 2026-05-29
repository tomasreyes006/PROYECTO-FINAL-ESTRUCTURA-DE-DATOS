package proyectoed.modelos;

import java.io.Serializable;

/**
 * @authors Tomás Reyes, Juan Mateus, Santiago Rey, David Barbosa
 */
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombres;
    private String correoUnab;
    private String carrera;
    private String contrasena;
    private int edad;
    private int rachaDias;
    private String ultimaFechaIngreso;

    public Usuario(String nombres, String correoUnab, String carrera, String contrasena, int edad, int rachaDias, String ultimaFechaIngreso) {
        this.nombres = nombres;
        this.correoUnab = correoUnab;
        this.carrera = carrera;
        this.contrasena = contrasena;
        this.edad = edad;
        this.rachaDias = rachaDias;
        this.ultimaFechaIngreso = ultimaFechaIngreso;
    }

    public Usuario(String nombres, String correoUnab, String carrera, String contrasena, int edad) {
        this.nombres = nombres;
        this.correoUnab = correoUnab;
        this.carrera = carrera;
        this.contrasena = contrasena;
        this.edad = edad;
        this.rachaDias = 1; // Todo usuario nuevo inicia con racha de 1 día
        this.ultimaFechaIngreso = java.time.LocalDate.now().toString(); // Fecha de hoy (AAAA-MM-DD)
    }

    public String getNombres() { return nombres; }
    public String getCorreoUnab() { return correoUnab; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getCarrera() { return carrera; }
    public int getEdad() { return edad; }
    public int getRachaDias() { return rachaDias; }
    public void setRachaDias(int rachaDias) { this.rachaDias = rachaDias; }
    public String getUltimaFechaIngreso() { return ultimaFechaIngreso; }
    public void setUltimaFechaIngreso(String ultimaFechaIngreso) { this.ultimaFechaIngreso = ultimaFechaIngreso; }
}