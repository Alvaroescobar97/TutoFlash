package co.ajeg.tutoflash.model.materia;

import java.io.Serializable;
import java.util.List;

public class MateriaTutor implements Serializable {

    private String id;
    private String autorId;
    private String publicacionId;
    private String tutorId;
    private String descripcion;
    private String precio;
    private List<String> fechas;

    public MateriaTutor(){}

    public MateriaTutor(String id, String autorId, String publicacionId, String tutorId, String descripcion, String precio, List<String> fechas) {
        this.id = id;
        this.autorId = autorId;
        this.publicacionId = publicacionId;
        this.tutorId = tutorId;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fechas = fechas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAutorId() {
        return autorId;
    }

    public void setAutorId(String autorId) {
        this.autorId = autorId;
    }

    public String getPublicacionId() {
        return publicacionId;
    }

    public void setPublicacionId(String publicacionId) {
        this.publicacionId = publicacionId;
    }

    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public List<String> getFechas() {
        return fechas;
    }

    public void setFechas(List<String> fechas) {
        this.fechas = fechas;
    }
}
