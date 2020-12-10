package co.ajeg.tutoflash.model.chat;

import java.io.Serializable;

public class ChatMensaje implements Serializable {

    private String id;
    private String autorId;
    private String mensaje;
    private String date;

    public ChatMensaje(){}

    public ChatMensaje(String id, String autorId, String mensaje, String date) {
        this.id = id;
        this.autorId = autorId;
        this.mensaje = mensaje;
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

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
