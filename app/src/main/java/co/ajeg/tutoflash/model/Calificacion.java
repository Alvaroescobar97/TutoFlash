package co.ajeg.tutoflash.model;

import java.io.Serializable;

public class Calificacion implements Serializable {

    private String uid;
    private String userId;
    private float value;

    public Calificacion(){

    }

    public Calificacion(String uid, String userId, float value) {
        this.uid = uid;
        this.userId = userId;
        this.value = value;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
