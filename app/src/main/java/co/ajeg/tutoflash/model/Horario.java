package co.ajeg.tutoflash.model;

import java.io.Serializable;

public class Horario implements Serializable {

    private String id;
    private String fecha;
    private String mensaje;

    public Horario(){}

    public Horario(String id, String fecha, String mensaje) {
        this.id = id;
        this.fecha = fecha;
        this.mensaje = mensaje;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
