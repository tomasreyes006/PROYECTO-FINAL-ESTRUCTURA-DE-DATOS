package proyectoed.utilidades;

import java.io.*;
import proyectoed.pantallas.VentanaPrincipal.DunabCRUD;
import proyectoed.pantallas.VentanaPrincipal.EncuentroCRUD;

public class GestorPersistencia {

    private static final String ARCHIVO_DUNAB = "datos_dunab.dat";
    private static final String ARCHIVO_ENCUENTROS = "datos_encuentros.dat";

    public static void guardarTodo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DUNAB))) {
            oos.writeInt(DunabCRUD.saldoDunab);
            oos.writeObject(DunabCRUD.historialTransacciones);
            oos.writeObject(DunabCRUD.encuentrosInscritos);
            oos.writeObject(DunabCRUD.inventarioTienda);
            oos.writeObject(DunabCRUD.historialAcciones);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_ENCUENTROS))) {
            oos.writeObject(EncuentroCRUD.encuentros);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void cargarTodo() {
        File fDunab = new File(ARCHIVO_DUNAB);
        if (fDunab.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fDunab))) {
                DunabCRUD.saldoDunab = ois.readInt();
                DunabCRUD.historialTransacciones = (java.util.List) ois.readObject();
                DunabCRUD.encuentrosInscritos = (java.util.List) ois.readObject();
                DunabCRUD.inventarioTienda = (java.util.List) ois.readObject();
                DunabCRUD.historialAcciones = (proyectoed.tda.PilaEnlazada) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            DunabCRUD.inicializarDatos();
        }

        File fEnc = new File(ARCHIVO_ENCUENTROS);
        if (fEnc.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fEnc))) {
                EncuentroCRUD.encuentros = (java.util.List) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            EncuentroCRUD.encuentros = EncuentroCRUD.cargarEncuentros();
        }
    }
}
