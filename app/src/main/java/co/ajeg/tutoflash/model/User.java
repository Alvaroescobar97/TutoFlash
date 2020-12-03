package co.ajeg.tutoflash.model;

import java.io.Serializable;

public class User implements Serializable {

    private String id;
    private String name;
    private String email;
    private String carrera;

    public User() {
    }

    public User(String id, String name, String email, String carrera) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.carrera = carrera;
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
}
