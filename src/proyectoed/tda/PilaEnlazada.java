package proyectoed.tda;

import java.io.Serializable;

public class PilaEnlazada<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Nodo<T> tope;
    private int tamaño;

    public PilaEnlazada() {
        this.tope = null;
        this.tamaño = 0;
    }

    public void apilar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        nuevo.setSiguiente(tope);
        tope = nuevo;
        tamaño++;
    }

    public T desapilar() {
        if (estaVacia()) return null;
        T dato = tope.getDato();
        tope = tope.getSiguiente();
        tamaño--;
        return dato;
    }

    public boolean estaVacia() { return tope == null; }
    public int getTamaño() { return tamaño; }
    
    public String[] toArrayString() {
        String[] resultado = new String[tamaño];
        Nodo<T> actual = tope;
        int i = 0;
        while (actual != null && i < tamaño) {
            resultado[i] = actual.getDato().toString();
            actual = actual.getSiguiente();
            i++;
        }
        return resultado;
    }
}
