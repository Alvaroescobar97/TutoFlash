package co.ajeg.tutoflash.model.notificacion;

import java.io.Serializable;

public class Notificacion implements Serializable {

    private String id;
    private String type;
    private String refId;
    private String title;
    private String descripcion;
    private long fecha;

    public Notificacion(){}

    public Notificacion(String id, String type, String refId, String title, String descripcion, long fecha) {
        this.id = id;
        this.type = type;
        this.refId = refId;
        this.title = title;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
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

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }
}
