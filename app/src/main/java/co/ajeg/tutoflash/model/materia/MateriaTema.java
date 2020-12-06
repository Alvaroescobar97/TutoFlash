package co.ajeg.tutoflash.model.materia;

import java.io.Serializable;

public class MateriaTema implements Serializable {

    private String id;
    private String autorId;
    private String title;
    private String descripcion;
    private String informacion;
    private String tiempo;
    private long date;

    public MateriaTema(){}

    public MateriaTema(String id, String autorId, String title, String descripcion, String informacion, String tiempo, long date) {
        this.id = id;
        this.autorId = autorId;
        this.title = title;
        this.descripcion = descripcion;
        this.informacion = informacion;
        this.tiempo = tiempo;
        this.date = date;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
