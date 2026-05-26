package proyectoed;

import java.util.ArrayList;

/**
 *
 * @authors Tomás Reyes, Juan Mateus, Santiago Rey, David Barbosa
 */
public class DunabCRUD {
    public static ArrayList<Transaccion> historialTransacciones = new ArrayList<>();
    public static ArrayList<Encuentro> encuentrosDisponibles = new ArrayList<>();
    public static ArrayList<Encuentro> encuentrosInscritos = new ArrayList<>();
    public static ArrayList<Producto> inventarioTienda = new ArrayList<>();
    public static int saldoDunab = 1500;

    public static void inicializarDatos() {
        if (inventarioTienda.isEmpty()) {
            inventarioTienda.add(new Producto("Bono Almuerzo Bienestar", 400, "Alimentos", 50));
            inventarioTienda.add(new Producto("Pase Libre Gimnasio UNAB (Semana)", 600, "Salud", 20));
            inventarioTienda.add(new Producto("Cuaderno Institucional DUNAB", 300, "Útiles", 15));
        }
        if (historialTransacciones.isEmpty()) {
            historialTransacciones.add(new Transaccion("Bono de Bienvenida", 1000, "2026-05-25", "INGRESO"));
            historialTransacciones.add(new Transaccion("Quiz Diagnóstico Estructuras", 500, "2026-05-25", "INGRESO"));
        }
    }
}