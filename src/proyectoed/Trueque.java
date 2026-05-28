package proyectoed;

/**
 *
 * @authors Tomás Reyes, Juan Mateus, David Barbosa, Santiago Rey
 */
public class Trueque {
    
    private String idTrueque;
    private String estudianteOrigen;
    private String estudianteDestino;
    private String articuloOfrecido;
    private String articuloSolicitado;
    private String estado; 

    public Trueque(String idTrueque, String estudianteOrigen, String articuloOfrecido, String articuloSolicitado) {
        this.idTrueque = idTrueque;
        this.estudianteOrigen = estudianteOrigen;
        this.articuloOfrecido = articuloOfrecido;
        this.articuloSolicitado = articuloSolicitado;
        this.estado = "Pendiente";
    }

    public String getIdTrueque() { return idTrueque; }
    public String getEstudianteOrigen() { return estudianteOrigen; }
    public String getEstudianteDestino() { return estudianteDestino; }
    public void setEstudianteDestino(String estudianteDestino) { this.estudianteDestino = estudianteDestino; }
    public String getArticuloOfrecido() { return articuloOfrecido; }
    public String getArticuloSolicitado() { return articuloSolicitado; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Trueque [" + estado + "]: " + estudianteOrigen + " ofrece " + articuloOfrecido + " por " + articuloSolicitado;
    }
}
