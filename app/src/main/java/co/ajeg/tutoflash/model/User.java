package co.ajeg.tutoflash.model;

import java.io.Serializable;

public class User implements Serializable {


    private String id;
    private long date;
    private String name;
    private String email;
    private String carrera;
    private String image;
    private int type;
    private float calificacion;

    public User() {
    }

    public User(String id, long date, String name, String email, String carrera, String image, int type, float calificacion) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.email = email;
        this.carrera = carrera;
        this.image = image;
        this.type = type;
        this.calificacion = calificacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }
}
