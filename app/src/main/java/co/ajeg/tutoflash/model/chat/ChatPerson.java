package co.ajeg.tutoflash.model.chat;

public class ChatPerson {

    String id;

    String miId;
    String myName;
    String myCarrera;
    String myPhotoId;

    String refId;
    String refName;
    String refCarrera;
    String refPhotoId;

    String dateLast;

    public ChatPerson(String id, String miId, String myName, String myCarrera, String myPhotoId, String refId, String refName, String refCarrera, String refPhotoId, String dateLast) {
        this.id = id;
        this.miId = miId;
        this.myName = myName;
        this.myCarrera = myCarrera;
        this.myPhotoId = myPhotoId;
        this.refId = refId;
        this.refName = refName;
        this.refCarrera = refCarrera;
        this.refPhotoId = refPhotoId;
        this.dateLast = dateLast;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMiId() {
        return miId;
    }

    public void setMiId(String miId) {
        this.miId = miId;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getMyCarrera() {
        return myCarrera;
    }

    public void setMyCarrera(String myCarrera) {
        this.myCarrera = myCarrera;
    }

    public String getMyPhotoId() {
        return myPhotoId;
    }

    public void setMyPhotoId(String myPhotoId) {
        this.myPhotoId = myPhotoId;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public String getRefCarrera() {
        return refCarrera;
    }

    public void setRefCarrera(String refCarrera) {
        this.refCarrera = refCarrera;
    }

    public String getRefPhotoId() {
        return refPhotoId;
    }

    public void setRefPhotoId(String refPhotoId) {
        this.refPhotoId = refPhotoId;
    }

    public String getDateLast() {
        return dateLast;
    }

    public void setDateLast(String dateLast) {
        this.dateLast = dateLast;
    }
}
