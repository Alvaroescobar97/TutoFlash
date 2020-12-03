package co.ajeg.tutoflash.model.chat;

public class ChatMensaje {

    private String id;
    private String autorId;

    private String mensaje;

    public ChatMensaje(){}

    public ChatMensaje(String id, String autorId, String mensaje) {
        this.id = id;
        this.autorId = autorId;
        this.mensaje = mensaje;
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
}
