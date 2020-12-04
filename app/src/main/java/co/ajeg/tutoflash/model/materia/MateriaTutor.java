package co.ajeg.tutoflash.model.materia;

public class MateriaTutor {

    private String id;
    private String tutorId;
    private String descripcion;
    private String precio;
    private String fecha;

    public MateriaTutor(){}

    public MateriaTutor(String id, String tutorId, String descripcion, String precio, String fecha) {
        this.id = id;
        this.tutorId = tutorId;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
