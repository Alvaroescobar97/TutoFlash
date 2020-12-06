package co.ajeg.tutoflash.model.chat;

import java.io.Serializable;

public class ChatPerson implements Serializable {

    private String id;

    private String sujectAId;
    private String sujectBId;

    private String dateLast;

    public ChatPerson(){}

    public ChatPerson(String id, String sujectAId, String sujectBId, String dateLast) {
        this.id = id;
        this.sujectAId = sujectAId;
        this.sujectBId = sujectBId;
        this.dateLast = dateLast;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSujectAId() {
        return sujectAId;
    }

    public void setSujectAId(String sujectAId) {
        this.sujectAId = sujectAId;
    }

    public String getSujectBId() {
        return sujectBId;
    }

    public void setSujectBId(String sujectBId) {
        this.sujectBId = sujectBId;
    }

    public String getDateLast() {
        return dateLast;
    }

    public void setDateLast(String dateLast) {
        this.dateLast = dateLast;
    }
}
