package proyectoed.pantallas;

import proyectoed.modelos.Encuentro;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @authors Tomás Reyes, Juan Mateus, Santiago Rey, David Barbosa
 */
public class EncuentroCRUD {
    
    public static ArrayList<Encuentro> encuentros = new ArrayList<Encuentro>();
    public static Encuentro encuentro;
    public static String fichero = "archivos.dat";

    public static void crearEncuentro(){
        String actividad;
        String descripcion;
        String fecha;
        String hora;
        actividad = JOptionPane.showInputDialog("Escribe la actividad a la cual le vas a agendar un encuentro: ");
        descripcion = JOptionPane.showInputDialog("Escribe su descripción: ");
        fecha = JOptionPane.showInputDialog("Escribe la fecha del encuentro a agendar: ");
        hora = JOptionPane.showInputDialog("Escribe la hora del encuentro: ");
        encuentro = new Encuentro(actividad, descripcion, fecha, hora);
        encuentros.add(encuentro);
    }
        
    public static String listarEncuentros() {
        String texto = "";
        for(Encuentro encuentro : encuentros){
            texto += "\n" + " [ " + (encuentros.indexOf(encuentro) + 1) + " ] " + encuentro;
        }
        return texto;
    }
        
    public static void cancelarEncuentro() {
        if (encuentros.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay encuentros registrados para eliminar.");
            return;
        }
        int indice = Integer.parseInt(JOptionPane.showInputDialog(
                "Indica el # del encuentro a eliminar: " + listarEncuentros()));
        if (indice > 0 && indice <= encuentros.size()) {
            encuentros.remove(indice - 1);
            JOptionPane.showMessageDialog(null, "El encuentro ha sido eliminado \n");
        } else {
            JOptionPane.showMessageDialog(null, "Número de encuentro no válido.");
        }
    }
        
    public static void modificarEncuentro() {
        if (encuentros.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay encuentros registrados para modificar.");
            return;
        }

        int indice = Integer.parseInt(JOptionPane.showInputDialog(
                "Indica el # del encuentro a modificar:\n" + listarEncuentros()));
        
        int posicion = indice - 1;

        if (posicion >= 0 && posicion < encuentros.size()) {
            Encuentro encuentroAEditar = encuentros.get(posicion);

            String nuevaActividad = JOptionPane.showInputDialog("Nuevo nombre de la actividad (Actual: " + encuentroAEditar.getActividad() + "):");
            String nuevaDescripcion = JOptionPane.showInputDialog("Nueva descripción (Actual: " + encuentroAEditar.getDescripcion() + "):");
            String nuevaFecha = JOptionPane.showInputDialog("Nueva fecha (Actual: " + encuentroAEditar.getFecha() + "):");
            String nuevaHora = JOptionPane.showInputDialog("Nueva hora (Actual: " + encuentroAEditar.getHora() + "):");

            encuentroAEditar.setActividad(nuevaActividad);
            encuentroAEditar.setDescripcion(nuevaDescripcion);
            encuentroAEditar.setFecha(nuevaFecha);
            encuentroAEditar.setHora(nuevaHora);

            JOptionPane.showMessageDialog(null, "¡Encuentro modificado con éxito!");
        } else {
            JOptionPane.showMessageDialog(null, "Número de encuentro no válido.");
        }
    }
        
    public static void main(String[] args) {
        encuentros = cargarEncuentros();
        int opcion = 0;

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "Encuentros!\nIngrese la opcion que desea realizar: \n 1. Agregar \n 2. Eliminar \n 3. Modificar \n 4. Listar \n 5. Guardar y Salir"));
            switch (opcion) {
                case 1:
                    crearEncuentro();
                    break;
                case 2:
                    cancelarEncuentro();
                    break;
                case 3:
                    modificarEncuentro();
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, listarEncuentros());
                    break;
                case 5:
                    guardarEncuentros();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opcion no valida!\n");
                    break;
            }
        } while (opcion != 5);
        JOptionPane.showMessageDialog(null, "Adios!");
    }
    
    public static ArrayList<Encuentro> cargarEncuentros() {
        ArrayList<Encuentro> lista = new ArrayList<Encuentro>();
        File archivo = new File(fichero);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lista;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero))) {
            while (true) {
                try {
                    Object aux = ois.readObject();
                    if (aux instanceof Encuentro) {
                        lista.add((Encuentro) aux);
                    }
                } catch (java.io.EOFException e) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            
        }
        return lista;
    }

    public static void guardarEncuentros() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero))) {
            for (Encuentro enc : encuentros) {
                oos.writeObject(enc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}