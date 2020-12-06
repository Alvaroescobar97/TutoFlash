package co.ajeg.tutoflash.model.materia;

public class Materia {

    private String id;
    private String name;
    private String lastFecha;
    private int nEntradas;

    public Materia() {
    }

    public Materia(String id, String name, String lastFecha, int nEntradas) {
        this.id = id;
        this.name = name;
        this.lastFecha = lastFecha;
        this.nEntradas = nEntradas;
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

    public String getLastFecha() {
        return lastFecha;
    }

    public void setLastFecha(String lastFecha) {
        this.lastFecha = lastFecha;
    }

    public int getnEntradas() {
        return nEntradas;
    }

    public void setnEntradas(int nEntradas) {
        this.nEntradas = nEntradas;
    }
}
