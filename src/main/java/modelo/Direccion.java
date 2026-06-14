package modelo;

public class Direccion implements Comparable<Direccion> {
    private int id;
    private String zona;
    private String numeroCasa;
    private Distrito distrito;

    public Direccion() {
    }

    public Direccion(int id) {
        this.id = id;
    }
    
    public Direccion(int id, String zona, String numeroCasa) {
        this.id = id;
        this.zona = zona;
        this.numeroCasa = numeroCasa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    @Override
    public int compareTo(Direccion dir) {
        return Integer.compare(this.id, dir.id);
    }
}
