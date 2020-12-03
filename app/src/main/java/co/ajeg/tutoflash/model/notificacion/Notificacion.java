package co.ajeg.tutoflash.model.notificacion;

public class Notificacion {

    private String id;
    private String type;
    private String title;
    private String informacion;
    private String descripcion;
    private String imgUrl;


    public Notificacion(String id, String type, String title, String informacion, String descripcion, String imgUrl) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.informacion = informacion;
        this.descripcion = descripcion;
        this.imgUrl = imgUrl;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
