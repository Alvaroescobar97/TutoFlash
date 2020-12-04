package co.ajeg.tutoflash.model.materia;

public class Materia {

    private String id;
    private String name;
    private String imagen;
    private String fecha;


    public Materia() {
    }

    public Materia(String id, String name, String imagen, String fecha) {
        this.id = id;
        this.name = name;
        this.imagen = imagen;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
