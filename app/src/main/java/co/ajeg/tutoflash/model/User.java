package co.ajeg.tutoflash.model;

import java.io.Serializable;

public class User implements Serializable {


    private String id;
    private String date;
    private String name;
    private String email;
    private String carrera;
    private String image;

    public User() {
    }

    public User(String id, String date, String name, String email, String carrera, String image) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.email = email;
        this.carrera = carrera;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
}
