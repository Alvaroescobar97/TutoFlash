package co.ajeg.tutoflash.model;

public class Tutoria {

    private String id;
    private String descripcion;
    private String fecha;

    public Tutoria(){

    }

    public Tutoria(String id, String descripcion, String fecha) {
        this.id = id;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
